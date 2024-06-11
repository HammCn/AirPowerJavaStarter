package cn.hamm.demo.module.open.base;

import cn.hamm.airpower.model.Json;
import cn.hamm.airpower.util.Utils;
import cn.hamm.demo.module.open.app.OpenAppEntity;

import java.util.Objects;

/**
 * <h1>开放接口的响应对象</h1>
 *
 * @author Hamm.cn
 */
public class OpenResponse {
    /**
     * <h2>加密响应数据</h2>
     *
     * @param openApp 应用
     * @param data    数据
     * @return 加密后的数据
     */
    public static String encodeResponse(OpenAppEntity openApp, Object data) {
        String response = null;
        if (Objects.isNull(data)) {
            // 数据负载为空 直接返回
            return response;
        }
        response = Json.toString(data);
        OpenArithmeticType appArithmeticType = Utils.getDictionaryUtil().getDictionary(
                OpenArithmeticType.class, openApp.getArithmetic()
        );
        try {
            switch (appArithmeticType) {
                case AES:
                    response = Utils.getAesUtil().setKey(openApp.getAppSecret()).encrypt(response);
                    break;
                case RSA:
                    response = Utils.getRsaUtil().setPrivateKey(openApp.getPrivateKey()).publicKeyEncrypt(response);
                    break;
                default:
            }
        } catch (Exception e) {
            OpenApiError.ENCRYPT_DATA_FAIL.show();
        }
        return response;
    }
}
