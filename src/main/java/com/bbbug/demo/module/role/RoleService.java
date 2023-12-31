package com.bbbug.demo.module.role;

import cn.hamm.airpower.result.Result;
import com.bbbug.demo.base.BaseService;
import org.springframework.stereotype.Service;

/**
 * <h1>Service</h1>
 *
 * @author Hamm
 */
@Service
public class RoleService extends BaseService<RoleEntity, RoleRepository> {
    @Override
    protected void beforeDelete(Long id) {
        RoleEntity entity = getById(id);
        Result.FORBIDDEN_DELETE.when(entity.getIsSystem(), "系统内置角色无法被删除!");
    }
}
