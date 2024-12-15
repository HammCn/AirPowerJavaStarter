package cn.hamm.demo.module.open.oauth;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.root.RootModel;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <h1>获取用户信息请求</h1>
 *
 * @author Hamm
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class OauthAccessTokenRequest extends RootModel<OauthAccessTokenRequest> {
    @Description("AccessToken")
    @NotBlank(groups = {IOauthAction.WhenAccessTokenRequired.class}, message = "AccessToken不能为空")
    private String accessToken;

}
