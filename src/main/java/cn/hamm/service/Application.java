package cn.hamm.service;

import cn.hamm.airpower.config.GlobalConfig;
import cn.hamm.airpower.security.PasswordUtil;
import cn.hamm.service.web.app.AppEntity;
import cn.hamm.service.web.app.AppService;
import cn.hamm.service.web.permission.PermissionService;
import cn.hamm.service.web.role.RoleEntity;
import cn.hamm.service.web.role.RoleService;
import cn.hamm.service.web.unit.UnitEntity;
import cn.hamm.service.web.unit.UnitService;
import cn.hamm.service.web.user.UserEntity;
import cn.hamm.service.web.user.UserService;
import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>入口类</h1>
 *
 * @author Hamm
 */
@EnableAutoConfiguration
@ComponentScan({"cn.hamm.service", "cn.hamm.airpower"})
public class Application {
    public static void main(String[] args) {
        GlobalConfig.isCacheEnabled = false;
        SpringApplication.run(Application.class, args);
        initDataSource();
        initDatabase();
        permissionService.forceReloadAllPermissions();
        System.out.println("---------------------------------");
        System.out.println("   Hi Guy, Service is running!   ");
        System.out.println("   URL:  http://127.0.0.1:8080   ");
        System.out.println("---------------------------------");
    }

    private static void initDataSource() {

    }

    private static void initDatabase() {
        RoleEntity firstRole = roleService.add(new RoleEntity()
                .setName("超级管理员")
                .setIsSystem(true)
                .setRemark("超级管理员角色组,请勿数据库暴力直接删除"));
        List<RoleEntity> roleList = new ArrayList<>();
        roleList.add(firstRole);
        String salt = RandomUtil.randomString(4);
        userService.add(new UserEntity()
                .setNickname("Hamm")
                .setEmail("admin@hamm.cn")
                .setPassword(PasswordUtil.encode("12345678", salt))
                .setSalt(salt)
                .setIsSystem(true)
                .setId(1L)
                .setRoleList(roleList)
                .setRemark("超级管理员,请勿数据库暴力直接删除"));

        System.out.println("---------------------------------");
        System.out.println("初始化第一个用户成功!\n");
        System.out.println("邮箱: admin@hamm.cn");
        System.out.println("密码: 12345678");

        //noinspection SpellCheckingInspection
        appService.add(new AppEntity().setAppKey("abcd").setAppName("基础服务").setAppSecret(RandomUtil.randomString(32).toUpperCase()));

        unitService.add(new UnitEntity().setId(1L).setName("斤"));
    }

    private static UserService userService;
    private static RoleService roleService;
    private static PermissionService permissionService;
    private static AppService appService;
    private static UnitService unitService;

    @Autowired
    private void setDatastore(
            UserService userService,
            RoleService roleService,
            PermissionService permissionService,
            AppService appService,
            UnitService unitService
    ) {
        Application.userService = userService;
        Application.roleService = roleService;
        Application.permissionService = permissionService;
        Application.appService = appService;
        Application.unitService = unitService;
    }

}
