package cn.hamm.demo;

import cn.hamm.airpower.helper.AirHelper;
import cn.hamm.airpower.util.PasswordUtil;
import cn.hamm.airpower.util.RandomUtil;
import cn.hamm.demo.common.Services;
import cn.hamm.demo.module.user.UserEntity;
import cn.hamm.demo.module.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
                .setPassword(PasswordUtil.encode("Aa123456", salt))
                .setRealName("凌小云")
                .setIdCard("500000200001010011")
                .setPhone("17666666666")
                .setSalt(salt));
        System.out.println("---------------------------------");
    }

    @Override
    public void run(String... args) {
        // 开始加载数据，请注意，以下数据请自行确保不会重复加载！！！
        loadUser();
        Services.getPermissionService().loadPermission();
        Services.getMenuService().loadMenu();
        // 所有数据检查完毕
        String[] localEnvList = {"local-hamm"};
        //noinspection StatementWithEmptyBody
        if (Arrays.stream(localEnvList).toList().contains(AirHelper.getCurrentEnvironment())) {
            // 其他需要在本地初始化的数据
        }
    }
}
