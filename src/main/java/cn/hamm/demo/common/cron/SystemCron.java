package cn.hamm.demo.common.cron;

import cn.hamm.airpower.config.ServiceConfig;
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
    private ServiceConfig serviceConfig;

    @Scheduled(cron = "59 59 23 * * *")
    void softShutdownService() {
        serviceConfig.setServiceRunning(false);
    }

    @Scheduled(cron = "0 0 0 * * *")
    void resetCodeRuleBaseNumber() {
        serviceConfig.setServiceRunning(true);
    }
}
