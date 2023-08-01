package cn.hamm.service.web.user;

import cn.hamm.airpower.result.Result;
import cn.hamm.airpower.root.RootService;
import cn.hamm.airpower.security.JwtUtil;
import cn.hamm.airpower.security.PasswordUtil;
import cn.hamm.airpower.util.EmailUtil;
import cn.hamm.service.exception.CustomResult;
import cn.hamm.service.web.app.AppVo;
import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <h1>Service</h1>
 *
 * @author Hamm
 */
@Service
public class UserService extends RootService<UserEntity, UserRepository> {
    /**
     * <h1>邮箱验证码key</h1>
     */
    private static final String REDIS_EMAIL_CODE_KEY = "email_code_";

    /**
     * <h1>OAUTH存储的key前缀</h1>
     */
    private static final String OAUTH_CODE_KEY = "oauth_code_";

    /**
     * <h1>COOKIE前缀</h1>
     */
    private static final String COOKIE_CODE_KEY = "cookie_code_";

    /**
     * <h1>验证码缓存</h1>
     */
    private static final int CACHE_CODE_EXPIRE_SECOND = 300;

    /**
     * <h1>Cookie缓存</h1>
     */
    private static final int CACHE_COOKIE_EXPIRE_SECOND = 86400;

    /**
     * <h1>修改密码</h1>
     *
     * @param userVo vo
     */
    public void modifyUserPassword(UserVo userVo) {
        UserEntity existUser = getById(userVo.getId());
        String code = getEmailCode(existUser.getEmail());
        Result.PARAM_INVALID.whenNotEquals(code, userVo.getCode(), "验证码输入错误");
        String oldPassword = userVo.getOldPassword();
        Result.PARAM_INVALID.whenNotEqualsIgnoreCase(
                PasswordUtil.encode(oldPassword, existUser.getSalt()),
                existUser.getPassword(),
                "原密码输入错误，修改密码失败"
        );
        String salt = RandomUtil.randomString(4);
        userVo.setSalt(salt);
        userVo.setPassword(PasswordUtil.encode(userVo.getPassword(), salt));
        removeEmailCodeCache(existUser.getEmail());
        update(userVo);
    }

    /**
     * <h1>删除指定邮箱的验证码缓存</h1>
     *
     * @param email 邮箱
     */
    public void removeEmailCodeCache(String email) {
        redisUtil.del(REDIS_EMAIL_CODE_KEY + email);
    }

    /**
     * <h1>重置密码</h1>
     *
     * @param userVo 用户实体
     */
    public void resetMyPassword(UserVo userVo) {
        String code = getEmailCode(userVo.getEmail());
        Result.PARAM_INVALID.whenNotEqualsIgnoreCase(code, userVo.getCode(), "邮箱验证码不一致");
        UserEntity existUser = repository.getByEmail(userVo.getEmail());
        Result.PARAM_INVALID.whenNull(existUser, "重置密码失败，用户信息异常");
        String salt = RandomUtil.randomString(4);
        existUser.setSalt(salt);
        existUser.setPassword(PasswordUtil.encode(userVo.getPassword(), salt));
        removeEmailCodeCache(existUser.getEmail());
        update(existUser);
    }

    /**
     * <h1>发送邮箱验证码</h1>
     *
     * @param email 邮箱
     */
    public void sendMail(String email) {
        CustomResult.EMAIL_SEND_BUSY.when(hasEmailCodeInRedis(email));
        String code = RandomUtil.randomNumbers(6);
        setCodeToRedis(email, code);
        EmailUtil.sendCode(email, "你收到一个邮箱验证码", code);
    }

    /**
     * <h1>存储OauthCode</h1>
     *
     * @param userId 用户ID
     * @param appVo  保存的应用信息
     */
    public void saveOauthCode(Long userId, AppVo appVo) {
        redisUtil.set(getAppCodeKey(appVo.getAppKey(), appVo.getCode()), userId, CACHE_CODE_EXPIRE_SECOND);
    }

    /**
     * <h1>获取指定应用的OauthCode缓存Key</h1>
     *
     * @param appKey 应用Key
     * @param code   Code
     * @return 缓存的Key
     */
    protected String getAppCodeKey(String appKey, String code) {
        return OAUTH_CODE_KEY + appKey + "_" + code;
    }

    /**
     * <h1>通过AppKey和Code获取用户ID</h1>
     *
     * @param appKey AppKey
     * @param code   Code
     * @return UserId
     */
    public Long getUserIdByOauthAppKeyAndCode(String appKey, String code) {
        Object userId = redisUtil.get(getAppCodeKey(appKey, code));
        Result.FORBIDDEN.whenNull(userId, "你的AppKey或Code错误，请重新获取");
        return Long.valueOf(userId.toString());
    }

    /**
     * <h1>删除AppOauthCode缓存</h1>
     *
     * @param appKey AppKey
     * @param code   Code
     */
    public void removeOauthCode(String appKey, String code) {
        redisUtil.del(getAppCodeKey(appKey, code));
    }

    /**
     * <h1>存储Cookie</h1>
     *
     * @param userId UserId
     * @param cookie Cookie
     */
    public void saveOauthCookie(Long userId, String cookie) {
        redisUtil.set(COOKIE_CODE_KEY + cookie, userId, CACHE_COOKIE_EXPIRE_SECOND);
    }

    /**
     * <h1>通过Cookie获取一个用户</h1>
     *
     * @param cookie Cookie
     * @return UserId
     */
    public Long getUserIdByCookie(String cookie) {
        Object userId = redisUtil.get(COOKIE_CODE_KEY + cookie);
        if (Objects.isNull(userId)) {
            return null;
        }
        return Long.valueOf(userId.toString());
    }

    /**
     * <h1>用户登录</h1>
     *
     * @param userEntity 用户实体
     * @return AccessToken
     */
    public String login(UserEntity userEntity) {
        UserEntity exitUser = null;
        if (Objects.nonNull(userEntity.getId())) {
            // ID登录
            exitUser = getById(userEntity.getId());
        } else if (Objects.nonNull(userEntity.getEmail())) {
            // 邮箱登录
            exitUser = repository.getByEmail(userEntity.getEmail());
        }
        CustomResult.USER_LOGIN_ACCOUNT_OR_PASSWORD_INVALID.whenNull(exitUser);
        // 将用户传入的密码加密与数据库存储匹配
        String encodePassword = PasswordUtil.encode(userEntity.getPassword(), exitUser.getSalt());
        CustomResult.USER_LOGIN_ACCOUNT_OR_PASSWORD_INVALID.whenNotEqualsIgnoreCase(encodePassword, exitUser.getPassword());
        // 创建一个AccessToken给用户
        return JwtUtil.getAccessToken(exitUser.getId().toString(), exitUser.getPassword());
    }

    /**
     * <h1>用户注册</h1>
     *
     * @param userVo 用户实体
     */
    public void register(UserVo userVo) {
        // 获取发送的验证码
        String code = getEmailCode(userVo.getEmail());
        Result.PARAM_INVALID.whenNotEquals(code, userVo.getCode(), "邮箱验证码不正确");
        // 验证邮箱是否已经注册过
        UserEntity existUser = repository.getByEmail(userVo.getEmail());
        CustomResult.USER_REGISTER_ERROR_EXIST.whenNotNull(existUser, "账号已存在,无法重复注册");
        // 获取一个随机盐
        String salt = RandomUtil.randomString(4);
        UserEntity newUser = new UserEntity();
        newUser.setEmail(userVo.getEmail());
        newUser.setSalt(salt);
        newUser.setPassword(PasswordUtil.encode(userVo.getPassword(), salt));
        add(newUser);
        //删掉使用过的邮箱验证码
        removeEmailCodeCache(userVo.getEmail());
    }
    /**
     * <h1>将验证码暂存到Redis</h1>
     *
     * @param email 邮箱
     * @param code  验证码
     */
    private void setCodeToRedis(String email, String code) {
        redisUtil.set(REDIS_EMAIL_CODE_KEY + email, code, CACHE_CODE_EXPIRE_SECOND);
    }

    /**
     * <h1>获取指定邮箱发送的验证码</h1>
     *
     * @param email 邮箱
     * @return 验证码
     */
    private String getEmailCode(String email) {
        Object code = redisUtil.get(REDIS_EMAIL_CODE_KEY + email);
        return Objects.isNull(code) ? "" : code.toString();
    }

    /**
     * <h1>指定邮箱验证码是否还在缓存内</h1>
     *
     * @param email 邮箱
     * @return 是否在缓存内
     */
    private boolean hasEmailCodeInRedis(String email) {
        return redisUtil.hasKey(REDIS_EMAIL_CODE_KEY + email);
    }

    @Override
    protected void beforeDelete(UserEntity entity) {
        Result.FORBIDDEN_DELETE.when(entity.getIsSystem(), "系统内置用户无法被删除!");
    }

    @Override
    protected UserEntity beforeAdd(UserEntity entity) {
        UserEntity existUser = repository.getByEmail(entity.getEmail());
        Result.FORBIDDEN_EXIST.whenNotNull(existUser, "邮箱已经存在，请勿重复添加用户");
        return entity;
    }
}
