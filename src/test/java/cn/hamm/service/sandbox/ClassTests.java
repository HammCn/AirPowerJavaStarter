package cn.hamm.service.sandbox;

import cn.hamm.airpower.result.json.Json;
import cn.hamm.airpower.root.RootEntity;
import cn.hamm.airpower.util.ReflectUtil;
import cn.hamm.service.web.user.UserEntity;
import org.junit.jupiter.api.Test;

/**
 * @author Hamm
 */
public class ClassTests {
    @Test
    void isEntityClass() {
        System.out.println(ReflectUtil.isEntity(Json.class));
        System.out.println(ReflectUtil.isEntity(UserEntity.class));
        System.out.println(ReflectUtil.isEntity(RootEntity.class));
    }

    @Test
    void isModelClass() {
        System.out.println(ReflectUtil.isModel(Json.class));
        System.out.println(ReflectUtil.isModel(UserEntity.class));
        System.out.println(ReflectUtil.isModel(RootEntity.class));
    }
}
