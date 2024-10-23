package cn.hamm.demo.common.interceptor;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.config.Constant;
import cn.hamm.airpower.exception.ServiceError;
import cn.hamm.airpower.interceptor.AbstractRequestInterceptor;
import cn.hamm.airpower.util.AccessTokenUtil;
import cn.hamm.airpower.util.PermissionUtil;
import cn.hamm.airpower.util.ReflectUtil;
import cn.hamm.airpower.util.RequestUtil;
import cn.hamm.demo.common.annotation.DisableLog;
import cn.hamm.demo.common.config.AppConstant;
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
 * @author Hamm.cn
 */
@Component
public class RequestInterceptor extends AbstractRequestInterceptor {
    /**
     * <h2>普通请求日志ID</h2>
     */
    static final String LOG_ID = "logId";

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private LogService logService;

    /**
     * <h2>验证指定的用户是否有指定权限标识的权限</h2>
     *
     * @param userId             用户ID
     * @param permissionIdentity 权限标识
     * @param request            请求对象
     * @apiNote 抛出异常则为拦截
     */
    @Override
    protected void checkUserPermission(Long userId, String permissionIdentity, HttpServletRequest request) {
        UserEntity existUser = userService.get(userId);
        if (existUser.isRootUser()) {
            return;
        }
        PermissionEntity needPermission = permissionService.getPermissionByIdentity(permissionIdentity);
        if (existUser.getRoleList().stream()
                .flatMap(role -> role.getPermissionList().stream())
                .anyMatch(permission -> Objects.equals(needPermission.getId(), permission.getId()))
        ) {
            return;
        }
        ServiceError.FORBIDDEN.show(String.format(
                "你无权访问 %s (%s)",
                needPermission.getName(),
                needPermission.getIdentity()
        ));
    }

    /**
     * <h2>拦截请求</h2>
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param clazz    控制器类
     * @param method   执行方法
     */
    @Override
    protected void interceptRequest(HttpServletRequest request, HttpServletResponse response, Class<?> clazz, Method method) {
        DisableLog disableLog = ReflectUtil.getAnnotation(DisableLog.class, method);
        if (Objects.nonNull(disableLog)) {
            return;
        }
        String accessToken = request.getHeader(serviceConfig.getAuthorizeHeader());
        Long userId = null;
        int appVersion = request.getIntHeader(AppConstant.APP_VERSION_HEADER);
        String platform = Constant.EMPTY_STRING;
        String action = request.getRequestURI();
        try {
            userId = AccessTokenUtil.create().getPayloadId(accessToken, serviceConfig.getAccessTokenSecret());
            platform = request.getHeader(AppConstant.APP_PLATFORM_HEADER);
            Description description = method.getAnnotation(Description.class);
            if (Objects.nonNull(description) && StringUtils.hasText(description.value())) {
                action = String.format("%s (%s)", description.value(), action);
            }
        } catch (Exception ignored) {
        }
        String identity = PermissionUtil.getPermissionIdentity(clazz, method);
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
        setShareData(LOG_ID, logId);
    }
}
