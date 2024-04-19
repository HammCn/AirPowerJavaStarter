package cn.hamm.demo.common.interceptor;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.config.GlobalConfig;
import cn.hamm.airpower.interceptor.AbstractRequestInterceptor;
import cn.hamm.airpower.request.RequestUtil;
import cn.hamm.airpower.result.Result;
import cn.hamm.airpower.security.AccessUtil;
import cn.hamm.airpower.security.SecurityUtil;
import cn.hamm.demo.common.config.AppConfig;
import cn.hamm.demo.common.config.Constant;
import cn.hamm.demo.module.role.RoleEntity;
import cn.hamm.demo.module.system.log.LogEntity;
import cn.hamm.demo.module.system.log.LogService;
import cn.hamm.demo.module.system.permission.PermissionEntity;
import cn.hamm.demo.module.system.permission.PermissionService;
import cn.hamm.demo.module.user.UserEntity;
import cn.hamm.demo.module.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * <h1>权限拦截器</h1>
 *
 * @author Hamm
 */
@Component
public class RequestInterceptor extends AbstractRequestInterceptor {
    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private LogService logService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private GlobalConfig globalConfig;

    @Autowired
    private AppConfig appConfig;

    /**
     * <h2>验证指定的用户是否有指定权限标识的权限</h2>
     *
     * @param userId             用户ID
     * @param permissionIdentity 权限标识
     * @param request            请求对象
     * @return 验证结果
     */
    @Override
    public boolean checkPermissionAccess(Long userId, String permissionIdentity, HttpServletRequest request) {
        UserEntity existUser = userService.get(userId);
        if (existUser.isRootUser()) {
            return true;
        }
        PermissionEntity needPermission = permissionService.getPermissionByIdentity(permissionIdentity);
        for (RoleEntity role : existUser.getRoleList()) {
            for (PermissionEntity permission : role.getPermissionList()) {
                if (needPermission.getId().equals(permission.getId())) {
                    return true;
                }
            }
        }
        Result.FORBIDDEN.show("你无权访问 " + needPermission.getName() + "(" + needPermission.getIdentity() + ")");
        return false;
    }

    @Override
    protected void beforeHandleRequest(HttpServletRequest request, HttpServletResponse response, Class<?> clazz, Method method) {
        String accessToken = request.getHeader(globalConfig.getAuthorizeHeader());
        Long userId = null;
        int appVersion = request.getIntHeader(Constant.APP_VERSION_HEADER);
        String platform = "";
        String action = request.getRequestURI();
        try {
            userId = securityUtil.getUserIdFromAccessToken(accessToken);
            platform = request.getHeader(Constant.APP_PLATFORM_HEADER);
            Description description = method.getAnnotation(Description.class);
            if (Objects.nonNull(description) && StringUtils.hasText(description.value())) {
                action = description.value() + "(" + action + ")";
            }
        } catch (Exception ignored) {
        }
        String identity = AccessUtil.getPermissionIdentity(clazz, method);
        PermissionEntity permissionEntity = permissionService.getPermissionByIdentity(identity);
        if (Objects.nonNull(permissionEntity)) {
            action = permissionEntity.getName();
        }
        long logId = logService.add(new LogEntity()
                .setIp(RequestUtil.getIpAddress(request))
                .setAction(action)
                .setPlatform(platform)
                .setRequest(getRequestBody(request))
                .setVersion(Math.max(1, appVersion))
                .setUserId(userId)
        );
        setShareData("logId", logId);
    }
}
