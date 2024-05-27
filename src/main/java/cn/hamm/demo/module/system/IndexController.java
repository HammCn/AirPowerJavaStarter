package cn.hamm.demo.module.system;

import cn.hamm.airpower.annotation.ApiController;
import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.DesensitizeExclude;
import cn.hamm.airpower.model.Json;
import cn.hamm.airpower.root.RootController;
import cn.hamm.airpower.util.Utils;
import cn.hamm.airpower.websocket.WebSocketPayload;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm.cn
 */
@ApiController("/")
@Description("首页")
public class IndexController extends RootController {
    @GetMapping("")
    @DesensitizeExclude
    public Json index() {
        Utils.getWebsocketUtil().publish(new WebSocketPayload().setType("index").setData("Hello World"));
        return Json.success("成功");
    }
}
