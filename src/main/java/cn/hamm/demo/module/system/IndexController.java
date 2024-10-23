package cn.hamm.demo.module.system;

import cn.hamm.airpower.annotation.ApiController;
import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.DesensitizeExclude;
import cn.hamm.airpower.helper.WebsocketHelper;
import cn.hamm.airpower.model.Json;
import cn.hamm.airpower.root.RootController;
import cn.hamm.airpower.websocket.WebSocketPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm.cn
 */
@ApiController("/")
@Description("首页")
public class IndexController extends RootController {
    @Autowired
    private WebsocketHelper websocketHelper;

    @GetMapping("")
    @DesensitizeExclude
    public Json index() {
        websocketHelper.publish(new WebSocketPayload().setData("全服消息"));
        websocketHelper.publishToUser(1L, new WebSocketPayload().setType("chat").setData("用户消息"));
        websocketHelper.publishToChannel("group_1", new WebSocketPayload().setType("gift").setData("频道礼物消息"));
        return Json.success("成功");
    }
}
