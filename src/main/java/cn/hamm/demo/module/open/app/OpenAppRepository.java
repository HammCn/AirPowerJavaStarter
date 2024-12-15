package cn.hamm.demo.module.open.app;

import cn.hamm.demo.base.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * <h1>数据库连接信息</h1>
 *
 * @author Hamm.cn
 */
@Repository
public interface OpenAppRepository extends BaseRepository<OpenAppEntity> {
    /**
     * <h3>通过AppKey查询应用</h3>
     *
     * @param appKey AppKey
     * @return 应用
     */
    OpenAppEntity getByAppKey(String appKey);
}
