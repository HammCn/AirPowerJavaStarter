package cn.hamm.demo.module.system.oauth2;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.config.CookieConfig;
import cn.hamm.airpower.result.Result;
import cn.hamm.airpower.result.json.JsonData;
import cn.hamm.airpower.root.RootController;
import cn.hamm.airpower.security.Permission;
import cn.hamm.airpower.security.SecurityUtil;
import cn.hamm.demo.common.config.AppConfig;
import cn.hamm.demo.module.system.app.AppEntity;
import cn.hamm.demo.module.system.app.AppService;
import cn.hamm.demo.module.user.UserEntity;
import cn.hamm.demo.module.user.UserService;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
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
@Slf4j
public class Oauth2Controller extends RootController {
    @Autowired
    private UserService userService;

    @Autowired
    private AppService appService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private CookieConfig cookieConfig;

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
            return redirectLogin(response, appKey, redirectUri);
        }
        String cookieString = null;
        for (Cookie c : cookies) {
            if (cookieConfig.getAuthCookieName().equals(c.getName())) {
                cookieString = c.getValue();
                break;
            }
        }
        if (StrUtil.isAllBlank(cookieString)) {
            // 没有cookie
            return redirectLogin(response, appKey, redirectUri);
        }
        Long userId = userService.getUserIdByCookie(cookieString);
        if (Objects.isNull(userId)) {
            // cookie没有找到用户
            return redirectLogin(response, appKey, redirectUri);
        }
        UserEntity userEntity = userService.get(userId);
        String code = RandomUtil.randomString(32);
        appEntity.setCode(code).setAppKey(appKey);
        userService.saveOauthCode(userEntity.getId(), appEntity);
        String redirectTarget = URLDecoder.decode(redirectUri, Charset.defaultCharset());
        String querySplit = "?";
        if (redirectTarget.contains(querySplit)) {
            redirectTarget += "&code=" + code;
        } else {
            redirectTarget += "?code=" + code;
        }
        redirect(response, redirectTarget);
        return null;
    }

    @Description("Code换取AccessToken")
    @Permission(login = false)
    @PostMapping("accessToken")
    public JsonData accessToken(@RequestBody @Validated({AppEntity.WhenCode2AccessToken.class}) AppEntity appEntity) {
        String code = appEntity.getCode();
        Long userId = userService.getUserIdByOauthAppKeyAndCode(appEntity.getAppKey(), code);
        AppEntity existApp = appService.getByAppKey(appEntity.getAppKey());
        Result.FORBIDDEN.whenNotEquals(existApp.getAppSecret(), appEntity.getAppSecret(), "应用秘钥错误");
        userService.removeOauthCode(existApp.getAppKey(), code);
        String accessToken = securityUtil.createAccessToken(userId);
        return new JsonData(accessToken);
    }

    private ModelAndView redirectLogin(HttpServletResponse response, String appKey, String redirectUri) {
        String url = appConfig.getLoginUrl() +
                "?appKey=" +
                appKey +
                "&redirectUri=" +
                redirectUri;
        redirect(response, url);
        return null;
    }

    /**
     * <h2>显示一个错误页面</h2>
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
     * <h2>重定向到指定的URL</h2>
     *
     * @param response 响应体
     * @param url      目标URL
     */
    private void redirect(HttpServletResponse response, String url) {
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
