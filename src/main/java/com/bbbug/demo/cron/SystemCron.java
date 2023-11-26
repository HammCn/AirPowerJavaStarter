package com.bbbug.demo.cron;

import cn.hamm.airpower.config.GlobalConfig;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <h1>系统定时任务</h1>
 *
 * @author hamm
 */
@Component
public class SystemCron {

    @Scheduled(cron = "59 59 23 * * *")
    void softShutdownService() {
        GlobalConfig.isServiceRunning = false;
    }

    @Scheduled(cron = "0 0 0 * * *")
    void resetCodeRuleBaseNumber() {
        GlobalConfig.isServiceRunning = true;
    }
}
