package cn.hamm.demo.module.system;

import cn.hamm.airpower.annotation.ApiController;
import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.Permission;
import cn.hamm.airpower.model.Json;
import cn.hamm.airpower.root.RootController;
import cn.hamm.airpower.util.Utils;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm.cn
 */
@ApiController("/test")
@Description("测试")
@Permission(login = false)
public class TestController extends RootController {
    @GetMapping("")
    public Json index() {
        return Json.success("Hello World!");
    }

    @GetMapping("ip")
    public Json ip() {
        return Json.success(Utils.getRequestUtil().getIpAddress(Utils.getRequest()));
    }
}
