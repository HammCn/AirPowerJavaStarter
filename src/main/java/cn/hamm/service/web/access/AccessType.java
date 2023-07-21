package cn.hamm.service.web.access;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.interfaces.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <h1>授权类型枚举</h1>
 *
 * @author Hamm
 */
@AllArgsConstructor
@Getter
@Description("授权类型")
public enum AccessType implements IEnum {
    /**
     * <h1>授权给用户</h1>
     */
    TO_USER(1, "授权给用户"),

    /**
     * <h1>授权给角色</h1>
     */
    TO_ROLE(2, "授权给角色");

    private final int value;
    private final String label;
}
