package cn.hamm.service.web.user;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.response.ResponseFilter;
import cn.hamm.airpower.result.Result;
import cn.hamm.airpower.result.json.Json;
import cn.hamm.airpower.result.json.JsonData;
import cn.hamm.airpower.root.RootEntityController;
import cn.hamm.airpower.security.JwtUtil;
import cn.hamm.airpower.security.Permission;
import cn.hamm.service.web.app.AppEntity;
import cn.hamm.service.web.app.AppService;
import cn.hamm.service.web.app.AppVo;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm
 */
@RestController
@RequestMapping("user")
@Description("用户")
public class UserController extends RootEntityController<UserService, UserVo> {
    @Autowired
    AppService appService;

    @Description("获取我的信息")
    @Permission(authorize = false)
    @PostMapping("getMyInfo")
    @ResponseFilter(UserEntity.WhenGetMyInfo.class)
    public JsonData getMyInfo(Long userId) {
        return jsonData(service.getById(userId));
    }

    @Description("修改我的信息")
    @Permission(authorize = false)
    @PostMapping("updateMyInfo")
    public Json updateMyInfo(@RequestBody @Validated({UserEntity.WhenUpdateMyInfo.class}) UserEntity userEntity, Long userId) {
        userEntity.setId(userId);
        userEntity.setRoleList(null);
        service.update(userEntity);
        return json("资料修改成功");
    }

    @Description("获取我的菜单")
    @Permission(authorize = false)
    @PostMapping("getMyMenuList")
    public JsonData getMyMenuList() {
        return jsonData(service.getMyMenuList());
    }

    @Description("获取我的权限")
    @Permission(authorize = false)
    @PostMapping("getMyPermissionList")
    public JsonData getMyPermissionList() {
        return jsonData(service.getMyPermissionList());
    }

    @Description("修改我的密码")
    @Permission(authorize = false)
    @PostMapping("updateMyPassword")
    public Json updateMyPassword(@RequestBody @Validated({UserEntity.WhenUpdateMyPassword.class}) UserVo userVo, Long userId) {
        userVo.setId(userId);
        service.modifyUserPassword(userVo);
        return json("密码修改成功");
    }

    @Description("找回密码")
    @Permission(authorize = false, login = false)
    @PostMapping("resetMyPassword")
    public Json resetMyPassword(@RequestBody @Validated(UserEntity.WhenResetMyPassword.class) UserVo userVo) {
        service.resetMyPassword(userVo);
        return json("密码重置成功");
    }

    @Description("注册账号")
    @Permission(authorize = false, login = false)
    @PostMapping("register")
    public Json register(@RequestBody @Validated({UserEntity.WhenRegister.class}) UserVo userVo) {
        service.register(userVo);
        return json("注册成功");
    }

    @Description("登录账号")
    @Permission(authorize = false, login = false)
    @PostMapping("login")
    public JsonData login(@RequestBody @Validated({UserEntity.WhenLogin.class}) UserVo userVo) {
        String accessToken = service.login(userVo);
        String appKey = userVo.getAppKey();
        if (StrUtil.isAllBlank(appKey)) {
            // 普通登录
            return jsonData(accessToken, "登录成功,请存储你的访问凭证");
        }
        // 应用登录
        AppEntity appEntity = appService.getByAppKey(appKey);
        AppVo appVo = appEntity.copyTo(AppVo.class);
        Result.PARAM_INVALID.whenNull(appEntity, "登录失败,错误的应用ID");
        Long userId = JwtUtil.getUserId(accessToken);
        String code = RandomUtil.randomString(32);
        appVo.setCode(code);
        service.saveOauthCode(userId, appVo);
        String cookie = RandomUtil.randomString(32);
        service.saveOauthCookie(userId, cookie);
        AppVo data = new AppVo().setCode(code).setCookie(cookie);
        return jsonData(data, "登录成功,请取出code重定向到redirectUri");
    }

}
