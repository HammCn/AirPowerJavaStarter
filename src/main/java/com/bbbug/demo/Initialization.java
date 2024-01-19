package com.bbbug.demo;

import cn.hamm.airpower.security.PasswordUtil;
import cn.hutool.core.util.RandomUtil;
import com.bbbug.demo.common.config.AppConfig;
import com.bbbug.demo.module.role.RoleEntity;
import com.bbbug.demo.module.role.RoleService;
import com.bbbug.demo.module.system.menu.MenuService;
import com.bbbug.demo.module.system.permission.PermissionService;
import com.bbbug.demo.module.system.unit.UnitService;
import com.bbbug.demo.module.user.UserEntity;
import com.bbbug.demo.module.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * <h1>初始化</h1>
 *
 * @author hamm
 */
@Component
public class Initialization {
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
    private UnitService unitService;

    public void run() {
        if (appConfig.isInitData()) {
            permissionService.initPermission();
            menuService.initMenu();
            initUserAndRole();
        }
    }

    private void initUserAndRole() {
        // 初始化角色
        RoleEntity firstRole = roleService.getMaybeNull(1L);
        if (Objects.nonNull(firstRole)) {
            return;
        }
        firstRole = roleService.add(new RoleEntity()
                .setName("超级管理员")
                .setCode("ROOT")
                .setIsSystem(true)
                .setRemark("超级管理员角色组,请勿数据库暴力直接删除"));

        // 初始化用户
        UserEntity userEntity = userService.getMaybeNull(1L);
        if (Objects.nonNull(userEntity)) {
            return;
        }
        Set<RoleEntity> roleList = new HashSet<>();
        roleList.add(firstRole);
        String salt = RandomUtil.randomString(4);
        userService.add(new UserEntity()
                .setNickname("Hamm")
                .setEmail("admin@hamm.cn")
                .setPassword(PasswordUtil.encode("Aa123456", salt))
                .setSalt(salt)
                .setIsSystem(true)
                .setId(1L)
                .setRoleList(roleList)
                .setRemark("超级管理员,请勿数据库暴力直接删除"));

        System.out.println("---------------------------------");
        System.out.println("初始化第一个用户成功!\n");
        System.out.println("邮箱: admin@hamm.cn");
        System.out.println("密码: 12345678");
    }

}
