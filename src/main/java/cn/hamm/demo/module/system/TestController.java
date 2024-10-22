package cn.hamm.demo.module.system;


import cn.hamm.airpower.core.annotation.ApiController;
import cn.hamm.airpower.core.annotation.Description;
import cn.hamm.airpower.core.annotation.Permission;
import cn.hamm.airpower.crud.root.RootController;

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
