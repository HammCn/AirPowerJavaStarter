package cn.hamm.demo.module.open.base;

import cn.hamm.airpower.model.Json;
import cn.hamm.airpower.root.RootModel;
import cn.hamm.airpower.util.Utils;
import cn.hamm.demo.module.open.app.OpenAppEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * <h1>OpenApi请求体</h1>
 *
 * @author Hamm.cn
 */
@Slf4j
@Data
public class OpenRequest {
    /**
     * <h2>AppKey</h2>
     */
    @NotBlank(message = "AppKey不能为空")
    private String appKey;

    /**
     * <h2>版本号</h2>
     */
    @NotNull(message = "版本号不能为空")
    private int version;

    /**
     * <h2>请求毫秒时间戳</h2>
     */
    @NotNull(message = "请求毫秒时间戳不能为空")
    private long timestamp;

    /**
     * <h2>加密后的业务数据</h2>
     */
    @NotNull(message = "业务数据包体不能为空")
    private String content;

    /**
     * <h2>签名字符串</h2>
     */
    @NotNull(message = "签名字符串不能为空")
    private String signature;

    /**
     * <h2>当前请求的应用</h2>
     */
    private OpenAppEntity openApp;

    /**
     * <h2>解密后的业务数据</h2>
     */
    private String decodeContent;

    /**
     * <h2>签名验证结果</h2>
     */
    public final void checkSignature() {
        String source = this.openApp.getAppSecret() + this.appKey + this.version + this.timestamp + this.content;
        OpenErrorCode.SIGNATURE_INVALID.whenNotEquals(this.signature, DigestUtils.sha1Hex(source));
    }

    /**
     * <h2>获取业务数据对象</h2>
     *
     * @param clazz 业务数据对象类型
     */
    public final <T extends RootModel<T>> T getRequest(Class<T> clazz) {
        return Json.parse(getRequest(), clazz);
    }

    /**
     * <h2>获取请求的数据</h2>
     *
     * @return 请求数据
     */
    public final String getRequest() {
        String request = this.content;
        OpenArithmeticType appArithmeticType = Utils.getDictionaryUtil().getDictionary(
                OpenArithmeticType.class, this.openApp.getArithmetic()
        );
        try {
            switch (appArithmeticType) {
                case AES:
                    request = Utils.getAesUtil().setKey(this.openApp.getAppSecret()).decrypt(request);
                    break;
                case RSA:
                    request = Utils.getRsaUtil().setPrivateKey(openApp.getPrivateKey()).privateKeyDecrypt(request);
                    break;
                default:
            }
        } catch (Exception e) {
            OpenErrorCode.DECRYPT_DATA_FAIL.show();
        }
        return request;
    }
}
