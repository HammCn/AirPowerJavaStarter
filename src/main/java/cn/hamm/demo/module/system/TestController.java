package cn.hamm.demo.module.system;


import cn.hamm.airpower.core.annotation.ApiController;
import cn.hamm.airpower.core.annotation.Description;
import cn.hamm.airpower.core.annotation.DesensitizeExclude;
import cn.hamm.airpower.core.annotation.Permission;
import cn.hamm.airpower.core.json.Json;
import cn.hamm.airpower.crud.root.RootController;
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
    @GetMapping("aaa")
    public Json index() {
        return Json.success("成功");
    }
}
