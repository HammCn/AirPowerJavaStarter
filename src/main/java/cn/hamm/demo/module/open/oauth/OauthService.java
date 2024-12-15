package cn.hamm.demo.module.open.oauth;

import cn.hamm.airpower.config.Constant;
import cn.hamm.airpower.exception.ServiceError;
import cn.hamm.airpower.helper.RedisHelper;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <h1>OauthService</h1>
 *
 * @author Hamm.cn
 */
@Service
public class OauthService {
    /**
     * <h3>Code缓存秒数</h3>
     */
    private static final int CACHE_CODE_EXPIRE_SECOND = Constant.SECOND_PER_MINUTE * 5;

    @Autowired
    private RedisHelper redisHelper;

    /**
     * <h3>用户ID的缓存Key</h3>
     *
     * @param appKey 应用Key
     * @param code   Code
     * @return 缓存的Key
     */
    @Contract(pure = true)
    public static @NotNull String getUserIdCacheKey(String appKey, String code) {
        return "oauth_user_" + appKey + "_" + code;
    }

    /**
     * <h3>Scope的缓存Key</h3>
     *
     * @param appKey 应用Key
     * @param code   Code
     * @return 缓存的Key
     */
    @Contract(pure = true)
    public static @NotNull String getScopeCacheKey(String appKey, String code) {
        return "oauth_scope_" + appKey + "_" + code;
    }

    /**
     * <h3>缓存用户ID</h3>
     *
     * @param appKey AppKey
     * @param code   Code
     * @param userId 用户ID
     */
    public void saveOauthUserCache(String appKey, String code, long userId) {
        redisHelper.set(getUserIdCacheKey(appKey, code), userId, CACHE_CODE_EXPIRE_SECOND);
    }

    /**
     * <h3>获取缓存的用户ID</h3>
     *
     * @param appKey AppKey
     * @param code   Code
     * @return UserId
     */
    public Long getOauthUserCache(String appKey, String code) {
        Object userId = redisHelper.get(getUserIdCacheKey(appKey, code));
        ServiceError.FORBIDDEN.whenNull(userId, "你的AppKey或Code错误，请重新获取");
        return Long.valueOf(userId.toString());
    }

    /**
     * <h3>删除缓存的用户ID</h3>
     *
     * @param appKey AppKey
     * @param code   Code
     */
    public void removeOauthUserCache(String appKey, String code) {
        redisHelper.del(getUserIdCacheKey(appKey, code));
    }

    /**
     * <h3>删除缓存的Scope</h3>
     *
     * @param appKey AppKey
     * @param code   Code
     */
    public void removeOauthScopeCache(String appKey, String code) {
        redisHelper.del(getScopeCacheKey(appKey, code));
    }

    /**
     * <h3>缓存Scope</h3>
     *
     * @param appKey AppKey
     * @param code   Code
     * @param scope  Scope
     */
    public void saveOauthScopeCache(String appKey, String code, String scope) {
        redisHelper.set(getScopeCacheKey(appKey, code), scope, CACHE_CODE_EXPIRE_SECOND);
    }

    /**
     * <h3>获取缓存的Scope</h3>
     *
     * @param appKey AppKey
     * @param code   Code
     * @return Scope
     */
    public String getOauthScopeCache(String appKey, String code) {
        Object object = redisHelper.get(getScopeCacheKey(appKey, code));
        if (Objects.isNull(object)) {
            return Constant.EMPTY_STRING;
        }
        return object.toString();
    }
}
