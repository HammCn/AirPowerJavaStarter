package cn.hamm.demo.module.user;

/**
 * <h1>用户行为</h1>
 *
 * @author Hamm
 */
public interface IUserAction {

    /**
     * ID+密码 邮箱+密码登录
     */
    interface WhenLogin {
    }

    /**
     * 邮箱+验证码登录
     */
    interface WhenLoginViaEmail {
    }

    interface WhenRegister {
    }

    interface WhenResetMyPassword {
    }

    interface WhenUpdateMyPassword {
    }

    interface WhenUpdateMyInfo {
    }

    interface WhenSendEmail {
    }

    interface WhenGetMyInfo {
    }
}
