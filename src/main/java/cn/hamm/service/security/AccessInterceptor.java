package cn.hamm.service.security;

import cn.hamm.airpower.result.Result;
import cn.hamm.airpower.security.AbstractAccessInterceptor;
import cn.hamm.service.web.access.AccessService;
import cn.hamm.service.web.permission.PermissionEntity;
import cn.hamm.service.web.permission.PermissionService;
import cn.hamm.service.web.user.UserEntity;
import cn.hamm.service.web.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <h1>权限拦截器</h1>
 *
 * @author Hamm
 */
public class AccessInterceptor extends AbstractAccessInterceptor {
    @Autowired
    private UserService userService;

    @Autowired
    private AccessService accessService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public String getUserPassword(Long userId) {
        UserEntity existUser = userService.getById(userId);
        Result.UNAUTHORIZED.whenNull(existUser);
        return existUser.getPassword();
    }

    @Override
    public boolean checkAccess(Long userId, String permissionIdentity) {
        UserEntity existUser = userService.getById(userId);
        PermissionEntity permission = permissionService.getPermissionByIdentity(permissionIdentity);
        return accessService.checkAccess(existUser, permission);
    }
}
