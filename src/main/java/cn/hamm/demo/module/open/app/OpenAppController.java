package cn.hamm.demo.module.open.app;

import cn.hamm.airpower.annotation.ApiController;
import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.Filter;
import cn.hamm.airpower.annotation.Permission;
import cn.hamm.airpower.exception.ServiceError;
import cn.hamm.airpower.model.Json;
import cn.hamm.airpower.model.query.QueryListRequest;
import cn.hamm.airpower.model.query.QueryPageRequest;
import cn.hamm.airpower.root.RootEntity;
import cn.hamm.airpower.util.RandomUtil;
import cn.hamm.demo.base.BaseController;
import cn.hamm.demo.module.user.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Base64;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm.cn
 */
@ApiController("open/app")
@Description("开放应用")
public class OpenAppController extends BaseController<OpenAppEntity, OpenAppService, OpenAppRepository> implements IOpenAppAction {
    @Autowired
    private UserService userService;

    @Description("通过AppKey获取应用信息")
    @PostMapping("getByAppKey")
    @Permission(login = false)
    @Filter(RootEntity.WhenGetDetail.class)
    public Json getByAppKey(@RequestBody @Validated(WhenGetByAppKey.class) OpenAppEntity openApp) {
        openApp = service.getByAppKey(openApp.getAppKey());
        ServiceError.DATA_NOT_FOUND.whenNull(openApp, "没有查到指定AppKey的应用");
        return Json.data(openApp);
    }

    @Override
    public Json add(@RequestBody @Validated(WhenAdd.class) @NotNull OpenAppEntity openApp) {
        openApp.setOwner(userService.get(getCurrentUserId()));
        openApp = service.get(service.add(openApp));
        return Json.data(String.format("应用名称: %s\n\nAppKey:\n%s\n\nAppSecret:\n%s\n\n公钥:\n%s", openApp.getAppName(), openApp.getAppKey(), openApp.getAppSecret(), openApp.getPublicKey()));
    }

    @Override
    protected void afterAdd(long id, OpenAppEntity source) {
        super.afterAdd(id, source);
    }

    @Description("重置密钥")
    @PostMapping("resetSecret")
    public Json resetSecret(@RequestBody @Validated(WhenIdRequired.class) OpenAppEntity openApp) {
        OpenAppEntity exist = service.get(openApp.getId());
        String appSecret = Base64.getEncoder().encodeToString(RandomUtil.randomBytes());
        exist.setAppSecret(appSecret);
        service.update(exist);
        return Json.data(appSecret);
    }

    @Description("重置密钥对")
    @PostMapping("resetKeyPair")
    public Json resetKeyPair(@RequestBody @Validated(WhenIdRequired.class) OpenAppEntity openApp) {
        OpenAppEntity exist = service.get(openApp.getId());
        service.resetKeyPare(exist);
        service.update(exist);
        return Json.data(exist.getPublicKey());
    }

    @Override
    protected void beforeDelete(long id) {
        OpenAppEntity openApp = service.get(id);
        ServiceError.FORBIDDEN_DELETE.whenNotEquals(openApp.getOwner().getId(), getCurrentUserId(), "你无权删除该应用");
    }

    @Override
    protected void beforeDisable(long id) {
        OpenAppEntity openApp = service.get(id);
        ServiceError.FORBIDDEN_DELETE.whenNotEquals(openApp.getOwner().getId(), getCurrentUserId(), "你无权禁用该应用");
    }

    @Override
    protected void beforeEnable(long id) {
        OpenAppEntity openApp = service.get(id);
        ServiceError.FORBIDDEN_DELETE.whenNotEquals(openApp.getOwner().getId(), getCurrentUserId(), "你无权启用该应用");
    }

    @Override
    protected QueryListRequest<OpenAppEntity> beforeGetList(@NotNull QueryListRequest<OpenAppEntity> queryListRequest) {
        queryListRequest.setFilter(queryListRequest.getFilter().setOwner(userService.get(getCurrentUserId())));
        return queryListRequest;
    }

    @Override
    protected QueryPageRequest<OpenAppEntity> beforeGetPage(@NotNull QueryPageRequest<OpenAppEntity> queryPageRequest) {
        queryPageRequest.setFilter(queryPageRequest.getFilter().setOwner(userService.get(getCurrentUserId())));
        return queryPageRequest;
    }

    @Override
    protected OpenAppEntity beforeAdd(@NotNull OpenAppEntity openApp) {
        openApp.setOwner(userService.get(getCurrentUserId()));
        return openApp.setAppKey(null).setAppSecret(null).setPublicKey(null).setPrivateKey(null);
    }

    @Override
    protected OpenAppEntity beforeUpdate(@NotNull OpenAppEntity openApp) {
        openApp.setOwner(userService.get(getCurrentUserId()));
        return openApp.setAppKey(null).setAppSecret(null).setPublicKey(null).setPrivateKey(null);
    }
}
