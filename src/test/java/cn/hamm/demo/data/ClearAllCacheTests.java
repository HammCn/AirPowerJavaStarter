package cn.hamm.demo.data;

import cn.hamm.airpower.util.redis.RedisUtil;
import cn.hamm.demo.BaseTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 删除所有缓存
 */
@SpringBootTest
class ClearAllCacheTests extends BaseTests {
    @Autowired
    RedisUtil redisUtil;

    @Test
    void clearAllCache(){
        printLine();
        print("开始移除所有缓存...");
        redisUtil.clearAll();
        print("所有权限初始化完成");
        printLine();
    }

}
