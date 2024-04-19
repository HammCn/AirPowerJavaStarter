package cn.hamm.demo;

import cn.hamm.airpower.security.PasswordUtil;
import cn.hamm.airpower.util.RandomUtil;
import cn.hamm.demo.common.config.AppConfig;
import cn.hamm.demo.module.role.RoleService;
import cn.hamm.demo.module.system.menu.MenuService;
import cn.hamm.demo.module.system.permission.PermissionService;
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
 * @author hamm
 */
@Component
public class Initialization implements CommandLineRunner {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private Environment environment;

    private void loadUser() {
        // 初始化用户
        UserEntity userEntity = userService.getMaybeNull(1L);
        if (Objects.nonNull(userEntity)) {
            return;
        }
        String salt = RandomUtil.randomString(4);
        userService.add(new UserEntity()
                .setNickname("Hamm")
                .setEmail("admin@hamm.cn")
                .setPassword(PasswordUtil.encode("Aa123456", salt))
                .setSalt(salt)
                .setRemark("超级管理员,请勿数据库暴力直接删除"));
        System.out.println("---------------------------------");
    }

    @Override
    public void run(String... args) {
        // 开始加载数据，请注意，以下数据请确保不会重复加载！！！
        loadUser();
        permissionService.loadPermission("cn.hamm.demo");
        menuService.loadMenu();
        // 所有数据检查完毕
        String activeProfile = environment.getActiveProfiles()[0];
        String[] localEnvList = {"hamm"};
        if (Arrays.stream(localEnvList).toList().contains(activeProfile)) {
            // 其他需要在本地初始化的数据
        }
    }
}
