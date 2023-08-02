package cn.hamm.service.web.role;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.Exclude;
import cn.hamm.airpower.annotation.Payload;
import cn.hamm.service.base.BaseEntity;
import cn.hamm.service.web.menu.MenuEntity;
import cn.hamm.service.web.permission.PermissionEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.Set;

/**
 * <h1>角色实体</h1>
 *
 * @author Hamm
 */
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "role")
@Description("角色")
public class RoleEntity extends BaseEntity<RoleEntity> {
    /**
     * <h1>角色名称</h1>
     */
    @Description("角色名称")
    @Column(columnDefinition = "varchar(255) default '' comment '角色名称'", unique = true)
    @NotBlank(groups = {WhenUpdate.class, WhenAdd.class}, message = "角色名称不能为空")
    private String name;

    /**
     * <h1>是否系统角色</h1>
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Description("是否系统角色")
    @Column(columnDefinition = "tinyint UNSIGNED default 0 comment '是否系统角色'")
    @Null(groups = {WhenUpdate.class, WhenAdd.class}, message = "是否系统角色这是个只读字段")
    @Exclude(filters = {WhenPayLoad.class})
    private Boolean isSystem;

    /**
     * <h1>角色的菜单列表</h1>
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @Payload
    @OrderBy("orderNo DESC")
    @Exclude(filters = {WhenPayLoad.class})
    @NotNull(groups = {WhenAuthorizeMenu.class}, message = "请传入授权的菜单列表")
    private Set<MenuEntity> menuList;

    /**
     * <h1>角色的权限列表</h1>
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @Payload
    @Exclude(filters = {WhenPayLoad.class})
    @NotNull(groups = {WhenAuthorizeMenu.class}, message = "请传入授权的权限列表")
    private Set<PermissionEntity> permissionList;

    /**
     * <h1>当授权菜单时</h1>
     */
    public interface WhenAuthorizeMenu {
    }

    /**
     * <h1>当授权权限时</h1>
     */
    public interface WhenAuthorizePermission {
    }
}
