package cn.hamm.demo.module.user;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.Exclude;
import cn.hamm.airpower.annotation.Payload;
import cn.hamm.airpower.annotation.Search;
import cn.hamm.demo.base.BaseEntity;
import cn.hamm.demo.module.role.RoleEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

/**
 * <h1>用户实体</h1>
 *
 * @author Hamm
 */
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "user")
@Description("用户")
public class UserEntity extends BaseEntity<UserEntity> implements IUserAction {
    /**
     * <h2>邮箱(唯一)</h2>
     */
    @Description("邮箱")
    @Column(columnDefinition = "varchar(255) default '' comment '邮箱'", unique = true)
    @NotBlank(groups = {WhenRegister.class, WhenResetMyPassword.class, WhenSendEmail.class, WhenAdd.class}, message = "邮箱不能为空")
    @Email(groups = {WhenRegister.class, WhenResetMyPassword.class, WhenSendEmail.class}, message = "邮箱格式不正确")
    @Null(groups = {WhenUpdateMyInfo.class}, message = "请勿传入email字段")
    @Search()
    private String email;

    /**
     * <h2>用户的密码(不返回给前端)</h2>
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Description("密码")
    @Column(columnDefinition = "varchar(255) default '' comment '密码'")
    @NotBlank(groups = {WhenLogin.class, WhenRegister.class, WhenResetMyPassword.class}, message = "密码不能为空")
    @Null(groups = {WhenUpdateMyInfo.class}, message = "请勿传入password字段")
    @Length(min = 6, message = "密码至少6位长度")
    private String password;

    /**
     * <h2>用户昵称</h2>
     */
    @Column(columnDefinition = "varchar(255) default '' comment '昵称'")
    @NotBlank(groups = {WhenUpdate.class, WhenAdd.class, WhenUpdateMyInfo.class}, message = "昵称不能为空")
    @Search()
    private String nickname;

    /**
     * <h2>密码盐</h2>
     */
    @JsonIgnore
    @Column(columnDefinition = "varchar(255) default '' comment '密码盐'")
    private String salt;

    /**
     * <h2>角色列表</h2>
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @Payload
    @Exclude(filters = {WhenPayLoad.class})
    private Set<RoleEntity> roleList;

    /**
     * <h2>登录使用的AppKey</h2>
     */
    @Transient
    private String appKey;

    /**
     * <h2>邮箱验证码</h2>
     */
    @NotBlank(groups = {WhenRegister.class, WhenUpdateMyPassword.class, WhenResetMyPassword.class}, message = "邮箱验证码不能为空")
    @Transient
    private String code;

    /**
     * <h2>修改密码时使用的原始密码</h2>
     */
    @NotBlank(groups = {WhenUpdateMyPassword.class}, message = "原始密码不能为空")
    @Transient
    private String oldPassword;

    /**
     * <h2>获取是否超级管理员</h2>
     *
     * @return 结果
     */
    public final boolean isRootUser() {
        return this.getId() == 1L;
    }
}
