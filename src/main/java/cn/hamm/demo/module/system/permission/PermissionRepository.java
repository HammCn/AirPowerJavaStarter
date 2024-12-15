package cn.hamm.demo.module.system.permission;

import cn.hamm.demo.base.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * <h1>Repository</h1>
 *
 * @author Hamm.cn
 */
@Repository
public interface PermissionRepository extends BaseRepository<PermissionEntity> {
    /**
     * <h3>根据权限标识获取一个权限</h3>
     *
     * @param identity 权限标识
     * @return 权限实体
     */
    PermissionEntity getByIdentity(String identity);
}
