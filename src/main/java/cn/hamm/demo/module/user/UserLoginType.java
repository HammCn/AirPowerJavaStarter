package cn.hamm.demo.module.user;

/**
 * <h1>用户登录方式</h1>
 *
 * @author Hamm.cn
 */
public enum UserLoginType {
    /**
     * <h2>ID+密码 邮箱+密码</h2>
     */
    VIA_ACCOUNT_PASSWORD,

    /**
     * <h2>邮箱+验证码</h2>
     */
    VIA_EMAIL_CODE,
}
