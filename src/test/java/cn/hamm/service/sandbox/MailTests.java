package cn.hamm.service.sandbox;

import cn.hamm.airpower.util.EmailUtil;
import org.junit.jupiter.api.Test;

/**
 * 邮件测试
 */
class MailTests {
    @Test
    void send(){
        EmailUtil.sendEmail("majhamm@qq.com", "这是标题", "这是详细内容");
    }
}
