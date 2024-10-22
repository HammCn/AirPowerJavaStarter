package cn.hamm.demo.base;

import cn.hamm.airpower.core.annotation.Description;
import cn.hamm.airpower.crud.root.RootEntity;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

/**
 * <h1>Entity</h1>
 *
 * @author Hamm.cn
 */
@MappedSuperclass
@Getter
@Description("")
public class BaseEntity<E extends BaseEntity<E>> extends RootEntity<E> {
}
