package cn.hamm.demo.base;

import cn.hamm.airpower.root.RootService;
import cn.hamm.airpower.util.ReflectUtil;
import cn.hamm.demo.common.Services;
import cn.hamm.demo.common.annotation.AutoGenerateCode;
import cn.hamm.demo.module.system.coderule.CodeRuleService;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * <h1>Service</h1>
 *
 * @param <E> 实体
 * @param <R> 数据源
 * @author Hamm.cn
 */
public class BaseService<E extends BaseEntity<E>, R extends BaseRepository<E>> extends RootService<E, R> {
    /**
     * <h3>当前服务的数据库最后一次确认</h3>
     *
     * @param entity 实体
     * @return 处理后的数据
     */
    protected E beforeAppSaveToDatabase(E entity) {
        return entity;
    }

    @Override
    protected final @NotNull E beforeSaveToDatabase(@NotNull E entity) {
        List<Field> fields = ReflectUtil.getFieldList(entity.getClass());
        CodeRuleService codeRuleService = Services.getCodeRuleService();
        for (Field field : fields) {
            AutoGenerateCode autoGenerateCode = ReflectUtil.getAnnotation(AutoGenerateCode.class, field);
            if (Objects.isNull(autoGenerateCode)) {
                continue;
            }
            Object value = ReflectUtil.getFieldValue(entity, field);
            if (!Objects.isNull(value) && StringUtils.hasText(value.toString())) {
                continue;
            }
            String code = codeRuleService.createCode(autoGenerateCode.value());
            ReflectUtil.setFieldValue(entity, field, code);
            break;
        }
        entity = beforeAppSaveToDatabase(entity);
        return entity;
    }
}
