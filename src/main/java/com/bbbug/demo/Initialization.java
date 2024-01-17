package com.bbbug.demo;

import cn.hamm.airpower.security.PasswordUtil;
import cn.hutool.core.util.RandomUtil;
import com.bbbug.demo.module.customer.CustomerEntity;
import com.bbbug.demo.module.customer.CustomerService;
import com.bbbug.demo.module.role.RoleEntity;
import com.bbbug.demo.module.role.RoleService;
import com.bbbug.demo.module.supplier.SupplierEntity;
import com.bbbug.demo.module.supplier.SupplierService;
import com.bbbug.demo.module.system.app.AppEntity;
import com.bbbug.demo.module.system.app.AppService;
import com.bbbug.demo.module.system.menu.MenuEntity;
import com.bbbug.demo.module.system.menu.MenuService;
import com.bbbug.demo.module.system.permission.PermissionService;
import com.bbbug.demo.module.system.unit.UnitEntity;
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
    private static UserService userService;
    private static RoleService roleService;
    private static PermissionService permissionService;
    private static AppService appService;
    private static MenuService menuService;
    private static UnitService unitService;
    private static CustomerService customerService;
    private static SupplierService supplierService;

    public static void run() {
        permissionService.initPermission();
        initUserAndRole();
        initUnitAndMaterial();
        initMenu();
        initOtherData();

    }

    private static void initOtherData() {
        appService.add(new AppEntity().setAppKey("airpower").setAppName("第三方应用").setUrl("").setAppSecret("abcdefghijklmnopqrstuvwxyz"));

        supplierService.add(new SupplierEntity().setCode("SP01").setName("三星屏幕套件"));
        customerService.add(new CustomerEntity().setCode("CUS01").setName("重庆解放碑AppleStore"));
    }

    private static void initMenu() {
        MenuEntity exist = menuService.getMaybeNull(1L);
        if (Objects.nonNull(exist)) {
            return;
        }
        MenuEntity homeMenu = new MenuEntity().setName("首页").setOrderNo(99).setPath("/console").setComponent("/console/index/index").setParentId(0L);
        menuService.add(homeMenu);

        // 人事管理
        MenuEntity userMenu = new MenuEntity().setName("人事管理").setOrderNo(88).setParentId(0L);
        userMenu = menuService.add(userMenu);

        MenuEntity userSubMenu;
        userSubMenu = new MenuEntity().setName("用户管理").setPath("/console/user/list").setParentId(userMenu.getId());
        menuService.add(userSubMenu);

        userSubMenu = new MenuEntity().setName("角色管理").setPath("/console/role/list").setParentId(userMenu.getId());
        menuService.add(userSubMenu);

        // 渠道管理
        MenuEntity sourceMenu = new MenuEntity().setName("渠道管理").setOrderNo(77).setParentId(0L);
        sourceMenu = menuService.add(sourceMenu);

        MenuEntity sourceSubMenu;

        sourceSubMenu = new MenuEntity().setName("供应商管理").setPath("/console/supplier/list").setParentId(sourceMenu.getId());
        menuService.add(sourceSubMenu);

        // 系统设置
        MenuEntity sysMenu = new MenuEntity().setName("系统设置").setOrderNo(2).setParentId(0L);
        sysMenu = menuService.add(sysMenu);

        MenuEntity sysSubMenu;

        sysSubMenu = new MenuEntity().setName("权限管理").setPath("/console/permission/list").setParentId(sysMenu.getId());
        menuService.add(sysSubMenu);

        sysSubMenu = new MenuEntity().setName("菜单管理").setPath("/console/menu/list").setParentId(sysMenu.getId());
        menuService.add(sysSubMenu);

        sysSubMenu = new MenuEntity().setName("第三方应用").setPath("/console/app/list").setParentId(sysMenu.getId());
        menuService.add(sysSubMenu);
    }

    private static void initUnitAndMaterial() {
        unitService.add(new UnitEntity().setCode("kg").setName("kg"));
    }

    private static void initUserAndRole() {
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

    @Autowired
    private void setDatastore(
            UserService userService,
            RoleService roleService,
            PermissionService permissionService,
            AppService appService,
            MenuService menuService,
            UnitService unitService,
            CustomerService customerService,
            SupplierService supplierService
    ) {
        Initialization.userService = userService;
        Initialization.roleService = roleService;
        Initialization.permissionService = permissionService;
        Initialization.appService = appService;
        Initialization.menuService = menuService;
        Initialization.unitService = unitService;
        Initialization.customerService = customerService;
        Initialization.supplierService = supplierService;
    }
}
