package cn.hamm.demo.module.personnel.role;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.demo.base.BaseEntity;
import cn.hamm.demo.module.system.menu.MenuEntity;
import cn.hamm.demo.module.system.permission.PermissionEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Set;

/**
 * <h1>角色实体</h1>
 *
 * @author Hamm.cn
 */
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "role")
@Description("角色")
public class RoleEntity extends BaseEntity<RoleEntity> implements IRoleAction {
    @Description("角色名称")
    @Column(columnDefinition = "varchar(255) default '' comment '角色名称'", unique = true)
    @NotBlank(groups = {WhenUpdate.class, WhenAdd.class}, message = "角色名称不能为空")
    private String name;

    @Description("角色的菜单列表")
    @ManyToMany(fetch = FetchType.EAGER)
    @OrderBy("orderNo DESC")
    @NotNull(groups = {WhenAuthorizeMenu.class}, message = "请传入授权的菜单列表")
    private Set<MenuEntity> menuList;

    @Description("角色的权限列表")
    @ManyToMany(fetch = FetchType.EAGER)
    @NotNull(groups = {WhenAuthorizeMenu.class}, message = "请传入授权的权限列表")
    private Set<PermissionEntity> permissionList;

}
