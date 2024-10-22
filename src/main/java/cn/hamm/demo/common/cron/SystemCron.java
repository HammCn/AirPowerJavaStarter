package cn.hamm.demo.common.cron;

import cn.hamm.airpower.crud.config.CrudConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <h1>系统定时任务</h1>
 *
 * @author Hamm.cn
 */
@Component
public class SystemCron {
    @Autowired
    private CrudConfig crudConfig;

    @Scheduled(cron = "59 59 23 * * *")
    void softShutdownService() {
        crudConfig.setServiceRunning(false);
    }

    @Scheduled(cron = "0 0 0 * * *")
    void resetCodeRuleBaseNumber() {
        crudConfig.setServiceRunning(true);
    }
}
