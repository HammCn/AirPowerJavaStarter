package cn.hamm.demo.module.system;

import cn.hamm.airpower.annotation.ApiController;
import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.Permission;
import cn.hamm.airpower.root.RootController;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm.cn
 */
@ApiController("/test")
@Description("测试")
@Permission(login = false)
public class TestController extends RootController {
}
