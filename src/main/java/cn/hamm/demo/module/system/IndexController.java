package cn.hamm.demo.module.system;

import cn.hamm.airpower.annotation.ApiController;
import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.DesensitizeExclude;
import cn.hamm.airpower.model.Json;
import cn.hamm.airpower.root.RootController;
import cn.hamm.airpower.task.TaskFlow;
import cn.hamm.demo.module.user.UserEntity;
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
        TaskFlow.init(new UserEntity().setNickname("Hamm").setId(1L).setEmail("admin@hamm.cn"))
                .next(user -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return TaskFlow.next(user.setNickname("Hamm2"));
                }).next(user -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(user.getNickname());
                    return TaskFlow.next(user.setNickname("Hamm3"));
                })
                .onSuccess(user -> {
                    System.out.println("Success " + user.getNickname());
                })
                .start();
        return Json.success("Hello World");
    }
}
