package cn.hamm.demo.module.open.oauth.enums;

import cn.hamm.airpower.interfaces.IDictionary;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <h1>授权类型</h1>
 *
 * @author Hamm.cn
 */
@Getter
@AllArgsConstructor
public enum OauthGrantType implements IDictionary {
    /**
     * <h3>授权码模式</h3>
     */
    AUTHORIZATION_CODE(1, "authorization_code", "授权码模式"),

    /**
     * <h3>刷新令牌模式</h3>
     */
    REFRESH_TOKEN(2, "refresh_token", "刷新令牌模式");

    private final int key;
    private final String label;
    private final String description;
}
