package cn.hamm.demo.common.cron;

import cn.hamm.airpower.config.GlobalConfig;
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
    private GlobalConfig globalConfig;

    @Scheduled(cron = "59 59 23 * * *")
    void softShutdownService() {
        globalConfig.setServiceRunning(false);
    }

    @Scheduled(cron = "0 0 0 * * *")
    void resetCodeRuleBaseNumber() {
        globalConfig.setServiceRunning(true);
    }
}
