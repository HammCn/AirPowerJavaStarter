package cn.hamm.service.security;

import cn.hamm.airpower.result.Result;
import cn.hamm.airpower.security.AbstractAccessInterceptor;
import cn.hamm.service.module.system.permission.PermissionEntity;
import cn.hamm.service.module.system.permission.PermissionService;
import cn.hamm.service.module.hr.role.RoleEntity;
import cn.hamm.service.module.hr.user.UserEntity;
import cn.hamm.service.module.hr.user.UserService;
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
}
