package cn.hamm.service.web.permission;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.service.base.BaseTreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

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
@Table(name = "permission")
@Description("权限")
public class PermissionEntity extends BaseTreeEntity<PermissionEntity> {
    /**
     * <h1>权限标识</h1>
     */
    @Description("权限标识")
    @Column(columnDefinition = "varchar(255) default '' comment '权限标识'", unique = true)
    @NotBlank(groups = {WhenUpdate.class, WhenAdd.class}, message = "权限标识不能为空")
    private String identity;

    /**
     * <h1>权限类型</h1>
     *
     * @see PermissionType
     */
    @Description("权限类型")
    @Column(columnDefinition = "tinyint UNSIGNED default 0 comment '权限类型'")
    private Integer type;

    /**
     * <h1>系统权限</h1>
     */
    @Description("系统权限")
    @Column(columnDefinition = "tinyint UNSIGNED default 0 comment '系统权限'")
    private Boolean isSystem;

}
