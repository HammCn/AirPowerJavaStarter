package cn.hamm.demo;

import cn.hamm.airpower.core.util.RandomUtil;
import cn.hamm.demo.common.Services;
import cn.hamm.demo.module.user.UserEntity;
import cn.hamm.demo.module.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

/**
 * <h1>初始化</h1>
 *
 * @author Hamm.cn
 */
@Component
public class Initialization implements CommandLineRunner {
    @Autowired
    private UserService userService;

    private void loadUser() {
        // 初始化用户
        UserEntity user = userService.getMaybeNull(1L);
        if (Objects.nonNull(user)) {
            return;
        }
        String salt = RandomUtil.randomString(UserService.PASSWORD_SALT_LENGTH);
        userService.add(new UserEntity()
                .setNickname("Hamm")
                .setEmail("admin@hamm.cn")
                .setPassword(userService.encodePassword("Aa123456", salt))
                .setSalt(salt)
                .setRemark("超级管理员,请勿数据库暴力直接删除"));
        System.out.println("---------------------------------");
    }

    @Autowired
    private Environment environment;

    @Override
    public void run(String... args) {
        // 开始加载数据，请注意，以下数据请自行确保不会重复加载！！！
        loadUser();
        Services.getPermissionService().loadPermission();
        Services.getMenuService().loadMenu();
        // 所有数据检查完毕
        String[] localEnvList = {"local-hamm"};
        //noinspection StatementWithEmptyBody
        if (Arrays.stream(localEnvList).toList().contains(environment.getActiveProfiles()[0])) {
            // 其他需要在本地初始化的数据
        }
    }
}
