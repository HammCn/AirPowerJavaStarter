package cn.hamm.demo.module.wechat;

import cn.hamm.airpower.root.RootController;
import cn.hamm.airpower.security.Permission;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Hamm https://hamm.cn
 */
@Permission(login = false)
@Controller
@RequestMapping("wechat")
public class WechatController extends RootController {
    @RequestMapping(value = "init", produces = "text/plain")
    public String init() {
        return "success";
    }
}
