package cn.hamm.service.module.system.app;

import cn.hamm.airpower.result.Result;
import cn.hamm.airpower.root.RootService;
import org.springframework.stereotype.Service;

/**
 * <h1>Service</h1>
 *
 * @author Hamm
 */
@Service
public class AppService extends RootService<AppEntity, AppRepository> {
    /**
     * <h1>通过AppKey获取一个应用</h1>
     *
     * @param appKey AppKey
     * @return AppEntity
     */
    public AppEntity getByAppKey(String appKey) {
        AppEntity appEntity = repository.getByAppKey(appKey);
        Result.NOT_FOUND.whenNull(appEntity, "没有查到指定AppKey的应用");
        return appEntity;
    }
}
