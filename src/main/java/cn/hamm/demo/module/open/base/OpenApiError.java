package cn.hamm.demo.module.open.base;

import cn.hamm.airpower.interfaces.IDictionary;
import cn.hamm.airpower.interfaces.IException;
import cn.hamm.demo.common.config.AppConstant;
import lombok.Getter;
import org.jetbrains.annotations.Contract;

@Getter
public enum OpenApiError implements IException, IDictionary {
    INVALID_APP_KEY(1, "传入的AppKey无效"),

    APP_DISABLED(2, "当前应用已被禁用"),

    SIGNATURE_INVALID(3, "签名无效"),

    DECRYPT_DATA_FAIL(4, "数据解密失败"),

    ENCRYPT_DATA_FAIL(5, "数据加密失败"),

    API_NOT_SUPPORT(6, "请求的API暂不支持"),

    REPEAT_REQUEST(7, "重复的请求"),

    TIMESTAMP_INVALID(8, "请求时间不在允许范围内"),

    JSON_DECODE_FAIL(9, "JSON解码失败"),
    ;

    private final int code;
    private final String message;

    @Contract(pure = true)
    OpenApiError(int code, String message) {
        this.code = code + AppConstant.BASE_OPEN_ERROR;
        this.message = message;
    }

    @Contract(pure = true)
    @Override
    public int getKey() {
        return this.code;
    }

    @Contract(pure = true)
    @Override
    public String getLabel() {
        return this.message;
    }
}
