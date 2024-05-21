package cn.hamm.demo;

import cn.hamm.airpower.util.Utils;
import cn.hamm.demo.common.Services;
import cn.hamm.demo.module.user.UserEntity;
import cn.hamm.demo.module.user.UserService;
import org.springframework.boot.CommandLineRunner;
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
    private void loadUser() {
        // 初始化用户
        UserEntity userEntity = Services.getUserService().getMaybeNull(1L);
        if (Objects.nonNull(userEntity)) {
            return;
        }
        String salt = Utils.getRandomUtil().randomString(UserService.PASSWORD_SALT_LENGTH);
        Services.getUserService().add(new UserEntity()
                .setNickname("Hamm")
                .setEmail("admin@hamm.cn")
                .setPassword(Utils.getPasswordUtil().encode("Aa123456", salt))
                .setSalt(salt)
                .setRemark("超级管理员,请勿数据库暴力直接删除"));
        System.out.println("---------------------------------");
    }

    @Override
    public void run(String... args) {
        // 开始加载数据，请注意，以下数据请确保不会重复加载！！！
        loadUser();
        Services.getPermissionService().loadPermission();
        Services.getMenuService().loadMenu();
        // 所有数据检查完毕
        String[] localEnvList = {"local-hamm"};
        //noinspection StatementWithEmptyBody
        if (Arrays.stream(localEnvList).toList().contains(Utils.getCurrentEnvironment())) {
            // 其他需要在本地初始化的数据
        }
    }
}
