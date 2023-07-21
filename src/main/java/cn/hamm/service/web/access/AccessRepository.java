package cn.hamm.service.web.access;

import cn.hamm.airpower.root.RootRepository;
import org.springframework.stereotype.Repository;

/**
 * <h1>数据库连接信息</h1>
 *
 * @author Hamm
 */
@Repository
public interface AccessRepository extends RootRepository<AccessEntity> {
    /**
     * <h1>查询一个授权</h1>
     *
     * @param permissionId 权限ID
     * @param accessTo     授权目标
     * @param accessType   授权类型
     * @return 授权
     * @see AccessType
     */
    AccessEntity getByPermissionIdAndAccessToAndAccessType(long permissionId, long accessTo, int accessType);

}
