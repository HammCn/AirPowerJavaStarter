package cn.hamm.demo.module.system.app;

import cn.hamm.demo.base.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * <h1>Repository</h1>
 *
 * @author Hamm.cn
 */
@Repository
public interface AppRepository extends BaseRepository<AppEntity> {
    /**
     * <h2>通过AppKey获取一个应用</h2>
     *
     * @param appKey AppKey
     * @return AppEntity
     */
    AppEntity getByAppKey(String appKey);
}
