package cn.hamm.demo.module.system.menu;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.Exclude;
import cn.hamm.demo.base.BaseTreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <h1>权限实体</h1>
 *
 * @author Hamm
 */
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "menu")
@Description("菜单")
public class MenuEntity extends BaseTreeEntity<MenuEntity> {
    /**
     * <h1>菜单路径</h1>
     */
    @Description("菜单路径")
    @Column(columnDefinition = "varchar(255) default '' comment '菜单路径'")
    private String path;

    /**
     * <h1>组件路径</h1>
     */
    @Description("组件路径")
    @Column(columnDefinition = "varchar(255) default '' comment '组件路径'")
    private String component;

    /**
     * <h1>菜单图标</h1>
     */
    @Description("菜单图标")
    @Column(columnDefinition = "varchar(255) default '' comment '菜单图标'")
    private String icon;

    /**
     * <h1>排序号</h1>
     */
    @Description("排序号")
    @Column(columnDefinition = "tinyint UNSIGNED default 0 comment '排序号'")
    @Exclude(filters = {WhenPayLoad.class})
    private Integer orderNo;
}
