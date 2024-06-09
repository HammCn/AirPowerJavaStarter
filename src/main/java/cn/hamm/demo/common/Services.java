package cn.hamm.demo.common;

import cn.hamm.demo.common.config.AppConfig;
import cn.hamm.demo.module.open.app.OpenAppService;
import cn.hamm.demo.module.open.log.OpenLogService;
import cn.hamm.demo.module.role.RoleService;
import cn.hamm.demo.module.system.log.LogService;
import cn.hamm.demo.module.system.menu.MenuService;
import cn.hamm.demo.module.system.permission.PermissionService;
import cn.hamm.demo.module.user.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * <h1>公共服务</h1>
 *
 * @author Hamm.cn
 */
@Service
public class Services {
    @Getter
    private static Environment environment;

    @Getter
    private static AppConfig appConfig;

    @Getter
    private static UserService userService;

    @Getter
    private static RoleService roleService;

    @Getter
    private static PermissionService permissionService;

    @Getter
    private static MenuService menuService;

    @Getter
    private static LogService logService;

    @Getter
    private static OpenAppService openAppService;

    @Getter
    private static OpenLogService openLogService;


    @Autowired
    public Services(
            Environment environment,
            AppConfig appConfig,
            UserService userService,
            RoleService roleService,
            PermissionService permissionService,
            MenuService menuService,
            LogService logService,
            OpenAppService openAppService,
            OpenLogService openLogService
    ) {
        Services.environment = environment;
        Services.appConfig = appConfig;
        Services.userService = userService;
        Services.roleService = roleService;
        Services.permissionService = permissionService;
        Services.menuService = menuService;
        Services.logService = logService;
        Services.openAppService = openAppService;
        Services.openLogService = openLogService;
    }
}
