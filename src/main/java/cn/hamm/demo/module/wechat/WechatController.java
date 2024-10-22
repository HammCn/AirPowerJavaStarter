package cn.hamm.demo.module.wechat;

import cn.hamm.airpower.core.annotation.Permission;
import cn.hamm.airpower.core.config.Constant;
import cn.hamm.airpower.crud.root.RootController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <h1>Wechat</h1>
 *
 * @author Hamm.cn
 */
@Permission(login = false)
@Controller
@RequestMapping("wechat")
public class WechatController extends RootController {
    @RequestMapping(value = "init", produces = "text/html")
    @ResponseBody
    public String init() {
        return Constant.SUCCESS;
    }
}
