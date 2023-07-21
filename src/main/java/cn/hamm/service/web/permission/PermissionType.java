package cn.hamm.service.web.permission;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.interfaces.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <h1>权限类型枚举</h1>
 *
 * @author Hamm
 */

@AllArgsConstructor
@Getter
@Description("权限类型")
public enum PermissionType implements IEnum {
    /**
     * <h1>分组</h1>
     */
    GROUP(1, "分组"),

    /**
     * <h1>接口</h1>
     */
    API(2, "接口"),

    /**
     * <h1>菜单</h1>
     */
    MENU(3, "菜单");

    private final int value;
    private final String label;
}
