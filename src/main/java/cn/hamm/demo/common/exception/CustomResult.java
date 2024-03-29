package cn.hamm.demo.common.exception;

import cn.hamm.airpower.result.IResult;
import lombok.Getter;

/**
 * <h1>应用自定义异常代码</h1>
 *
 * @author hamm
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
@Getter
public enum CustomResult implements IResult {
    EMAIL_SEND_BUSY(1, "发送邮件过于频繁，请稍后再试"),

    USER_LOGIN_ACCOUNT_OR_PASSWORD_INVALID(2, "用户账号或密码错误"),
    USER_REGISTER_ERROR_EXIST(3, "注册失败，账号已存在"),

    ;

    private final int code;
    private final String message;

    CustomResult(int code, String message) {
        this.code = code + 10000000;
        this.message = message;
    }
}
