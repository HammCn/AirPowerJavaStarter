package cn.hamm.service.web.access;

import cn.hamm.service.web.role.RoleEntity;
import cn.hamm.service.web.user.UserEntity;
import cn.hamm.service.web.permission.PermissionType;
import cn.hamm.service.web.permission.PermissionEntity;
import cn.hamm.airpower.root.RootService;
import cn.hamm.airpower.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <h1>Service</h1>
 *
 * @author Hamm
 */
@Service
public class AccessService extends RootService<AccessEntity, AccessRepository> {

    /**
     * <h1>检查用户是否被授权访问某个权限</h1>
     *
     * @param user       用户
     * @param permission 权限
     * @return 验证结果
     */
    public boolean checkAccess(UserEntity user, PermissionEntity permission) {
        Result.FORBIDDEN.whenNull(permission, "此接口不提供对外访问权限!");
        Result.FORBIDDEN.whenNotEquals(PermissionType.API.getValue(), permission.getType(), "你没有请求此接口的权限!");
        if (user.getIsSystem()) {
            // 用户是超级管理员
            return true;
        }
        AccessEntity accessUser = repository.getByPermissionIdAndAccessToAndAccessType(permission.getId(), user.getId(), AccessType.TO_USER.getValue());
        if (Objects.nonNull(accessUser) && accessUser.isAccessExpired()) {
            // 用户被授权访问 且未过期
            return true;
        }
        List<RoleEntity> roleList = user.getRoleList();
        for (RoleEntity role : roleList) {
            if (role.getIsSystem()) {
                // 用户所属角色是超级管理员
                return true;
            }
            AccessEntity accessRole = repository.getByPermissionIdAndAccessToAndAccessType(permission.getId(), role.getId(), AccessType.TO_ROLE.getValue());
            if (Objects.nonNull(accessRole) && accessRole.isAccessExpired()) {
                // 角色被授权 且未过期
                return true;
            }
        }
        Result.FORBIDDEN.show("你没有" + permission.getName() + "的权限(" + permission.getIdentity() + ")!");
        return false;
    }
}
