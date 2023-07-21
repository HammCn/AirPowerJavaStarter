package cn.hamm.service.web.oauth2;

import cn.hamm.service.web.app.AppService;
import cn.hamm.service.web.app.AppVo;
import cn.hamm.service.web.user.UserEntity;
import cn.hamm.service.web.user.UserService;
import cn.hamm.service.web.app.AppEntity;
import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.root.RootController;
import cn.hamm.airpower.result.Result;
import cn.hamm.airpower.result.json.JsonData;
import cn.hamm.airpower.security.JwtUtil;
import cn.hamm.airpower.security.Permission;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm
 */
@RestController
@RequestMapping("oauth2")
public class Oauth2Controller extends RootController {
    @Autowired
    public UserService userService;

    @Autowired
    public AppService appService;

    @GetMapping("authorize")
    public ModelAndView index(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String appKey = request.getParameter("appKey");
        if (StrUtil.isAllBlank(appKey)) {
            return showError("Invalid appKey!");
        }
        AppEntity appEntity;
        try {
            appEntity = appService.getByAppKey(appKey);
        } catch (Exception exception) {
            return showError("App(" + appKey + ") not found!");
        }
        String redirectUri = request.getParameter("redirectUri");
        if (StrUtil.isAllBlank(redirectUri)) {
            return showError("RedirectUri missing!");
        }
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            // 没有cookie
            return showLogin();
        }
        String cookie = null;
        for (Cookie c : cookies) {
            if ("ticket".equals(c.getName())) {
                cookie = c.getValue();
                break;
            }
        }
        if (StrUtil.isAllBlank(cookie)) {
            // 没有cookie
            return showLogin();
        }
        Long userId = userService.getUserIdByCookie(cookie);
        if (Objects.isNull(userId)) {
            // cookie没有找到用户
            return showLogin();
        }
        UserEntity userEntity = userService.getById(userId);
        String code = RandomUtil.randomString(32);
        AppVo appVo = appEntity.copyTo(AppVo.class);
        appVo.setCode(code).setAppKey(appKey);
        userService.saveOauthCode(userEntity.getId(), appVo);
        String redirectTarget;
        try {
            redirectTarget = URLDecoder.decode(redirectUri, Charset.defaultCharset().toString());
        } catch (UnsupportedEncodingException e) {
            return showLogin();
        }
        String querySplit = "?";
        if (redirectTarget.contains(querySplit)) {
            redirectTarget += "&code=" + code;
        } else {
            redirectTarget += "?code=" + code;
        }
        redirect(response, redirectTarget);
        return showLogin();
    }

    @Description("Code换取AccessToken")
    @Permission(login = false, authorize = false)
    @PostMapping("code2AccessToken")
    public JsonData code2AccessToken(@RequestBody @Validated({AppEntity.WhenCode2AccessToken.class}) AppVo appVo) {
        String code = appVo.getCode();
        Long userId = userService.getUserIdByOauthAppKeyAndCode(appVo.getAppKey(), code);
        AppEntity existApp = appService.getByAppKey(appVo.getAppKey());
        Result.FORBIDDEN.whenNotEquals(existApp.getAppSecret(), appVo.getAppSecret(), "应用秘钥错误");
        userService.removeOauthCode(existApp.getAppKey(), code);
        UserEntity userEntity = userService.getById(userId);
        String accessToken = JwtUtil.getAccessToken(userEntity.getId().toString(), userEntity.getPassword(), existApp.getId().toString());
        return new JsonData(accessToken);
    }

    private ModelAndView showLogin() {
        return new ModelAndView("authorize");
    }

    /**
     * <h1>显示一个错误页面</h1>
     *
     * @param error 错误信息
     * @return 错误页面
     */
    private ModelAndView showError(String error) {
        ModelAndView view = new ModelAndView("error");
        view.getModel().put("error", error);
        return view;
    }

    /**
     * <h1>重定向到指定的URL</h1>
     *
     * @param response 响应体
     * @param url      目标URL
     */
    private void redirect(HttpServletResponse response, String url) {
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
