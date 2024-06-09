package cn.hamm.demo.common.interceptor;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.config.Configs;
import cn.hamm.airpower.config.Constant;
import cn.hamm.airpower.config.MessageConstant;
import cn.hamm.airpower.enums.ServiceError;
import cn.hamm.airpower.interceptor.AbstractRequestInterceptor;
import cn.hamm.airpower.util.Utils;
import cn.hamm.demo.common.Services;
import cn.hamm.demo.common.annotation.DisableLog;
import cn.hamm.demo.common.annotation.OpenApi;
import cn.hamm.demo.common.config.AppConstant;
import cn.hamm.demo.module.system.log.LogEntity;
import cn.hamm.demo.module.system.permission.PermissionEntity;
import cn.hamm.demo.module.user.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
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
    static final String LOG_ID = "logId";

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
        UserEntity existUser = Services.getUserService().get(userId);
        if (existUser.isRootUser()) {
            return true;
        }
        PermissionEntity needPermission = Services.getPermissionService().getPermissionByIdentity(permissionIdentity);
        if (existUser.getRoleList().stream().flatMap(role -> role.getPermissionList().stream()).anyMatch(permission -> needPermission.getId().equals(permission.getId()))) {
            return true;
        }
        ServiceError.FORBIDDEN.show(String.format(
                MessageConstant.ACCESS_DENIED,
                needPermission.getName(),
                needPermission.getIdentity()
        ));
        return false;
    }

    @Override
    protected boolean beforeHandleRequest(@NotNull HttpServletRequest request, HttpServletResponse response, Class<?> clazz, Method method) {
        DisableLog disableLog = Utils.getReflectUtil().getAnnotation(DisableLog.class, method);
        if (Objects.nonNull(disableLog)) {
            return true;
        }
        OpenApi openApi = Utils.getReflectUtil().getAnnotation(OpenApi.class, method);
        if (Objects.nonNull(openApi)) {
            return true;
        }
        String accessToken = request.getHeader(Configs.getServiceConfig().getAuthorizeHeader());
        Long userId = null;
        int appVersion = request.getIntHeader(AppConstant.APP_VERSION_HEADER);
        String platform = Constant.EMPTY_STRING;
        String action = request.getRequestURI();
        try {
            userId = Utils.getSecurityUtil().getIdFromAccessToken(accessToken);
            platform = request.getHeader(AppConstant.APP_PLATFORM_HEADER);
            Description description = method.getAnnotation(Description.class);
            if (Objects.nonNull(description) && StringUtils.hasText(description.value())) {
                action = String.format(MessageConstant.MESSAGE_AND_DESCRIPTION, description.value(), action);
            }
        } catch (Exception ignored) {
        }
        String identity = Utils.getAccessUtil().getPermissionIdentity(clazz, method);
        PermissionEntity permissionEntity = Services.getPermissionService().getPermissionByIdentity(identity);
        if (Objects.nonNull(permissionEntity)) {
            action = permissionEntity.getName();
        }
        long logId = Services.getLogService().add(new LogEntity()
                .setIp(Utils.getRequestUtil().getIpAddress(request))
                .setAction(action)
                .setPlatform(platform)
                .setRequest(getRequestBody(request))
                .setVersion(Math.max(1, appVersion))
                .setUserId(userId)
        );
        setShareData(LOG_ID, logId);
        return true;
    }
}
