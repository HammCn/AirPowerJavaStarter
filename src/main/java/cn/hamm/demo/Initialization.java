package cn.hamm.demo;

import cn.hamm.airpower.helper.AirHelper;
import cn.hamm.airpower.util.PasswordUtil;
import cn.hamm.airpower.util.RandomUtil;
import cn.hamm.demo.common.Services;
import cn.hamm.demo.module.chat.room.RoomEntity;
import cn.hamm.demo.module.open.app.OpenAppEntity;
import cn.hamm.demo.module.open.app.OpenAppService;
import cn.hamm.demo.module.personnel.user.UserEntity;
import cn.hamm.demo.module.personnel.user.UserService;
import cn.hamm.demo.module.system.coderule.CodeRuleEntity;
import cn.hamm.demo.module.system.coderule.CodeRuleField;
import cn.hamm.demo.module.system.coderule.CodeRuleService;
import cn.hamm.demo.module.system.config.ConfigEntity;
import cn.hamm.demo.module.system.config.ConfigFlag;
import cn.hamm.demo.module.system.config.ConfigService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Component
public class Initialization implements CommandLineRunner {
    @Autowired
    private UserService userService;

    @Autowired
    private CodeRuleService codeRuleService;
    @Autowired
    private ConfigService configService;

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
        userService.add(new UserEntity()
                .setNickname("Ghost")
                .setEmail("hamm@hamm.cn")
                .setPassword(PasswordUtil.encode("Aa123456", salt))
                .setRealName("凌小云")
                .setIdCard("500000200001010012")
                .setPhone("17666666661")
                .setSalt(salt));
        System.out.println("---------------------------------");
        user = userService.getMaybeNull(1L);

        Services.getRoomService().add(new RoomEntity()
                .setName("广场")
                .setCode(666)
                .setIsOfficial(true)
                .setIsHot(true).setOwner(user)
        );

        Services.getRoomService().add(new RoomEntity()
                .setName("测试")
                .setCode(888)
                .setIsHot(true).setOwner(user)
        );
    }

    private void loadCodeRules() {
        CodeRuleField[] codeRuleFields = CodeRuleField.class.getEnumConstants();
        for (CodeRuleField codeRuleField : codeRuleFields) {
            CodeRuleEntity codeRule = codeRuleService.getByRuleField(codeRuleField.getKey());
            if (Objects.isNull(codeRule)) {
                codeRuleService.add(
                        new CodeRuleEntity()
                                .setIsSystem(true)
                                .setRuleField(codeRuleField.getKey())
                                .setPrefix(codeRuleField.getDefaultPrefix())
                                .setTemplate(codeRuleField.getDefaultTemplate())
                                .setSnType(codeRuleField.getDefaultSnType().getKey())
                );
            }
        }
    }

    private void loadConfigs() {
        ConfigFlag[] configFlags = ConfigFlag.class.getEnumConstants();
        for (ConfigFlag configFlag : configFlags) {
            try {
                ConfigEntity config = configService.get(configFlag);
                if (Objects.nonNull(config)) {
                    continue;
                }
            } catch (RuntimeException exception) {
                log.info("需要初始化配置");
            }
            configService.add(new ConfigEntity()
                    .setConfig(configFlag.getDefaultValue())
                    .setType(configFlag.getType().getKey())
                    .setName(configFlag.getLabel())
                    .setFlag(configFlag.name())
            );
        }
    }

    @Override
    public void run(String... args) {
        // 开始加载数据，请注意，以下数据请自行确保不会重复加载！！！
        loadUser();
        Services.getPermissionService().loadPermission();
        Services.getMenuService().loadMenu();
        // 所有数据检查完毕
        String[] localEnvList = {"local-hamm"};
        if (Arrays.stream(localEnvList).toList().contains(AirHelper.getCurrentEnvironment())) {
            // 其他需要在本地初始化的数据
            loadCodeRules();
            loadConfigs();
            OpenAppEntity openApp = new OpenAppEntity().setAppName("人力资源管理系统");
            OpenAppService openAppService = Services.getOpenAppService();
            openApp.setAppSecret("8bQfc5ddy4LkZb4TMgM4UwMfVkbIHiXiaHCXyqANAAc=")
                    .setAppKey("sy3ktITp0SZG5CXxzbJRomouyVH5oaOY")
                    .setUrl("https://demo.hamm.cn/app")
            ;
            openAppService.resetKeyPare(openApp);
            openAppService.add(openApp);
        }
    }
}
