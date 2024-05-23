package cn.hamm.demo.module.system;

import cn.hamm.airpower.annotation.ApiController;
import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.DesensitizeExclude;
import cn.hamm.airpower.model.Json;
import cn.hamm.airpower.model.TaskFlow;
import cn.hamm.airpower.root.RootController;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

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
        Bill bill = new Bill().setName("请假条");
        TaskFlow.init(bill)
                .next(this::lisiCheck)
                .next(this::wangwuCheck)
                .beforeStep(b -> System.out.println("处理前 " + Thread.currentThread().getName()))
                .afterStep(b -> System.out.println("处理后 " + Thread.currentThread().getName()))
                .onError((e, t) -> System.out.println("处理失败 " + e.getMessage()))
                .onSuccess(b -> System.out.println("审核结果 " + b.getSigns()))
                .start();
        return Json.success("成功");
    }

    TaskFlow<Bill> lisiCheck(Bill bill) {
        System.out.println("李四开始审核");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("李四审核完毕");
        bill.getSigns().add("李四");
        return TaskFlow.next(bill);
    }

    TaskFlow<Bill> wangwuCheck(Bill bill) {
        System.out.println("王武开始审核");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("王武审核完毕");
        bill.getSigns().add("王武");
        return TaskFlow.next(bill);
    }

    @Data
    @Accessors(chain = true)
    static class Bill {
        private String name;

        private List<String> signs = new ArrayList<>();
    }
}
