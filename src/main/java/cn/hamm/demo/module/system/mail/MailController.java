package cn.hamm.demo.module.system.mail;

import cn.hamm.airpower.annotation.ApiController;
import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.Permission;
import cn.hamm.airpower.model.Json;
import cn.hamm.airpower.root.RootController;
import cn.hamm.demo.common.Services;
import cn.hamm.demo.module.user.IUserAction;
import cn.hamm.demo.module.user.UserEntity;
import jakarta.mail.MessagingException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm.cn
 */
@ApiController("mail")
@Description("邮件")
public class MailController extends RootController implements IUserAction {
    @Description("发送邮件")
    @Permission(login = false)
    @PostMapping("send")
    public Json send(@RequestBody @Validated(WhenSendEmail.class) UserEntity user) throws MessagingException {
        Services.getUserService().sendMail(user.getEmail());
        return Json.success("发送成功");
    }
}
