package cn.hamm.demo.module.open.oauth.request;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.demo.module.open.oauth.OauthAppKeyRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <h1>创建Code请求</h1>
 *
 * @author Hamm
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class OauthGetAccessTokenRequest extends OauthAppKeyRequest {
    @Description("Code")
    @NotBlank(groups = {WhenGetAccessToken.class}, message = "Code不能为空")
    private String code;

    @Description("AppSecret")
    @NotBlank(groups = {WhenGetAccessToken.class}, message = "AppSecret不能为空")
    private String appSecret;


    /**
     * <h3>Code换AccessToken</h3>
     */
    public interface WhenGetAccessToken {
    }
}
