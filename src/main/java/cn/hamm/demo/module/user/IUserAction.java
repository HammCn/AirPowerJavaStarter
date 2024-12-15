package cn.hamm.demo.module.user;

/**
 * <h1>用户行为</h1>
 *
 * @author Hamm.cn
 */
public interface IUserAction {

    /**
     * <h3>ID+密码 邮箱+密码登录</h3>
     */
    interface WhenLogin {
    }

    /**
     * <h3>邮箱+验证码登录</h3>
     */
    interface WhenLoginViaEmail {
    }

    /**
     * <h3>密码重置</h3>
     */
    interface WhenResetMyPassword {
    }

    /**
     * <h3>修改密码</h3>
     */
    interface WhenUpdateMyPassword {
    }

    /**
     * <h3>修改资料</h3>
     */
    interface WhenUpdateMyInfo {
    }

    /**
     * <h3>发送邮件</h3>
     */
    interface WhenSendEmail {
    }

    /**
     * <h3>发送短信</h3>
     */
    interface WhenSendSms {
    }

    /**
     * <h3>获取我的信息</h3>
     */
    interface WhenGetMyInfo {
    }
}
