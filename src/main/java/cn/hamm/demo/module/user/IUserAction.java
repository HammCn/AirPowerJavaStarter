package cn.hamm.demo.module.user;

/**
 * <h1>用户行为</h1>
 *
 * @author Hamm.cn
 */
public interface IUserAction {

    /**
     * <h2>ID+密码 邮箱+密码登录</h2>
     */
    interface WhenLogin {
    }

    /**
     * <h2>邮箱+验证码登录</h2>
     */
    interface WhenLoginViaEmail {
    }

    /**
     * <h2>注册</h2>
     */
    interface WhenRegister {
    }

    /**
     * <h2>密码重置</h2>
     */
    interface WhenResetMyPassword {
    }

    /**
     * <h2>修改密码</h2>
     */
    interface WhenUpdateMyPassword {
    }

    /**
     * <h2>修改资料</h2>
     */
    interface WhenUpdateMyInfo {
    }

    /**
     * <h2>发送邮件</h2>
     */
    interface WhenSendEmail {
    }

    /**
     * <h2>获取我的信息</h2>
     */
    interface WhenGetMyInfo {
    }
}
