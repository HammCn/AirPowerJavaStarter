package cn.hamm.demo.common.exception;

import cn.hamm.airpower.interfaces.IDictionary;
import cn.hamm.airpower.interfaces.IException;
import cn.hamm.demo.common.config.AppConstant;
import lombok.Getter;
import org.jetbrains.annotations.Contract;

/**
 * <h1>应用自定义异常代码</h1>
 *
 * @author Hamm.cn
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
@Getter
public enum CustomError implements IException, IDictionary {
    EMAIL_SEND_BUSY(1, "发送邮件过于频繁，请稍后再试"),
    USER_LOGIN_ACCOUNT_OR_PASSWORD_INVALID(2, "用户账号或密码错误"),
    USER_REGISTER_ERROR_EXIST(3, "注册失败，账号已存在"),
    ;

    private final int code;
    private final String message;

    @Contract(pure = true)
    CustomError(int code, String message) {
        this.code = code + AppConstant.BASE_CUSTOM_ERROR;
        this.message = message;
    }

    /**
     * <h2>获取枚举的 {@code Key}</h2>
     *
     * @return {@code Key}
     */
    @Contract(pure = true)
    @Override
    public int getKey() {
        return code;
    }

    /**
     * <h2>获取枚举的描述</h2>
     *
     * @return 描述
     */
    @Contract(pure = true)
    @Override
    public String getLabel() {
        return message;
    }
}
