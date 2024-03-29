package cn.hamm.demo.common.security;

import cn.hamm.airpower.config.GlobalConfig;
import cn.hamm.airpower.request.RequestUtil;
import cn.hamm.airpower.result.Result;
import cn.hamm.airpower.security.AbstractAccessInterceptor;
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
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * <h1>权限拦截器</h1>
 *
 * @author Hamm
 */
public class AccessInterceptor extends AbstractAccessInterceptor {
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
        if (existUser.getIsSystem()) {
            return true;
        }
        PermissionEntity needPermission = permissionService.getPermissionByIdentity(permissionIdentity);
        for (RoleEntity role : existUser.getRoleList()) {
            if (role.getIsSystem()) {
                return true;
            }
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
    public void afterCompletion(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object object, Exception ex) {
        HandlerMethod handlerMethod = (HandlerMethod) object;
        //取出控制器和方法
        Class<?> clazz = handlerMethod.getBeanType();
        Method method = handlerMethod.getMethod();
        String accessToken = request.getHeader(globalConfig.getAuthorizeHeader());
        Long userId = null;
        int appVersion = -1;
        String platform = "";
        try {
            userId = securityUtil.getUserIdFromAccessToken(accessToken);
            appVersion = request.getIntHeader(Constant.APP_VERSION_HEADER);
            platform = request.getHeader(Constant.APP_PLATFORM_HEADER);
        } catch (Exception ignored) {

        }
        String identity = AccessUtil.getPermissionIdentity(clazz, method);
        PermissionEntity permissionEntity = permissionService.getPermissionByIdentity(identity);
        if (Objects.nonNull(permissionEntity)) {
            logService.add(new LogEntity().setIp(RequestUtil.getIpAddress(request))
                    .setAction(permissionEntity.getName())
                    .setPlatform(platform)
                    .setVersion(Math.max(1, appVersion))
                    .setUserId(userId));
        }
    }
}
