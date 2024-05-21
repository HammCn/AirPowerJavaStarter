package cn.hamm.demo.module.system.app;

import cn.hamm.airpower.annotation.ApiController;
import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.Filter;
import cn.hamm.airpower.annotation.Permission;
import cn.hamm.airpower.model.Json;
import cn.hamm.airpower.root.RootEntity;
import cn.hamm.demo.base.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm.cn
 */
@ApiController("app")
@Description("应用")
public class AppController extends BaseController<AppEntity, AppService, AppRepository> implements IAppAction {
    @Description("通过AppKey获取应用信息")
    @PostMapping("getByAppKey")
    @Permission(login = false)
    @Filter(RootEntity.WhenGetDetail.class)
    public Json getByAppKey(@RequestBody @Validated(WhenGetByAppKey.class) AppEntity entity) {
        return Json.data(service.getByAppKey(entity.getAppKey()));
    }

    @Description("重置指定应用的秘钥")
    @PostMapping("resetSecret")
    public Json resetSecret(@RequestBody @Validated(WhenResetSecret.class) AppEntity entity) {
        return Json.data(service.resetSecretById(entity.getId()), "重置应用秘钥成功");
    }
}
