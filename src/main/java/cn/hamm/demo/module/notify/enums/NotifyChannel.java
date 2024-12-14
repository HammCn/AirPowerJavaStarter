package cn.hamm.demo.module.notify.enums;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.interfaces.IDictionary;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <h1>通知渠道</h1>
 *
 * @author Hamm.cn
 */
@Getter
@AllArgsConstructor
@Description("通知渠道")
public enum NotifyChannel implements IDictionary {
    /**
     * <h2>企业微信</h2>
     */
    WORK_WECHAT(1, "企业微信"),

    /**
     * <h2>飞书</h2>
     */
    FEI_SHU(2, "飞书"),

    /**
     * <h2>钉钉</h2>
     */
    DING_TALK(3, "钉钉"),

    /**
     * <h2>邮件</h2>
     */
    EMAIL(4, "邮件"),

    /**
     * <h2>WebHook</h2>
     */
    WEB_HOOK(5, "WebHook");

    private final int key;
    private final String label;
}
