package cn.hamm.demo.module.webhook;

import cn.hamm.airpower.core.json.Json;
import cn.hamm.airpower.core.annotation.ApiController;
import cn.hamm.airpower.core.annotation.Description;
import cn.hamm.airpower.core.annotation.Permission;
import cn.hamm.airpower.core.config.Constant;
import cn.hamm.airpower.crud.interfaces.IEntityAction;
import cn.hamm.demo.base.BaseController;
import cn.hamm.demo.module.webhook.enums.WebHookScene;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.Map;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm.cn
 */
@ApiController("webhook")
@Description("通知钩子")
public class WebHookController extends BaseController<WebHookEntity, WebHookService, WebHookRepository> implements IEntityAction {
    @Description("获取支持通知的场景列表")
    @PostMapping("getSceneList")
    @Permission(authorize = false)
    public Json getSceneList() {
        return Json.data(Arrays.stream(WebHookScene.values())
                .map(item -> Map.of(
                        Constant.KEY, item.getKey(),
                        Constant.LABEL, item.getLabel()
                ))
                .toList()
        );
    }
}
