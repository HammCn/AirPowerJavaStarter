package com.bbbug.demo.module.system;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.root.RootController;
import cn.hamm.airpower.websocket.WebsocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm
 */
@RestController
@RequestMapping("/")
@Description("首页")
public class IndexController extends RootController {
    @Autowired
    private WebsocketUtil websocketUtil;

    @GetMapping("")
    public String index() {
        return "<h1>Hello World!</h1>";
    }
}
