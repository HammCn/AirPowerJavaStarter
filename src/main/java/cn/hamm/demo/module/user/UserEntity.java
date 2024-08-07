package cn.hamm.demo.module.user;

import cn.hamm.airpower.annotation.Description;
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
 * @author Hamm.cn
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
    @Description("邮箱")
    @Column(columnDefinition = "varchar(255) default '' comment '邮箱'", unique = true)
    @NotBlank(groups = {WhenRegister.class, WhenResetMyPassword.class, WhenSendEmail.class, WhenAdd.class}, message = "邮箱不能为空")
    @Email(groups = {WhenRegister.class, WhenResetMyPassword.class, WhenSendEmail.class}, message = "邮箱格式不正确")
    @Null(groups = {WhenUpdateMyInfo.class}, message = "请勿传入email字段")
    @Search()
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Description("密码")
    @Column(columnDefinition = "varchar(255) default '' comment '密码'")
    @NotBlank(groups = {WhenLogin.class, WhenRegister.class, WhenResetMyPassword.class}, message = "密码不能为空")
    @Null(groups = {WhenUpdateMyInfo.class}, message = "请勿传入password字段")
    @Length(min = 6, message = "密码至少6位长度")
    private String password;

    @Description("用户昵称")
    @Column(columnDefinition = "varchar(255) default '' comment '昵称'")
    @NotBlank(groups = {WhenUpdate.class, WhenAdd.class, WhenUpdateMyInfo.class}, message = "昵称不能为空")
    @Search()
    private String nickname;

    @Description("密码盐")
    @JsonIgnore
    @Column(columnDefinition = "varchar(255) default '' comment '密码盐'")
    private String salt;

    @Description("角色列表")
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<RoleEntity> roleList;

    @Description("登录使用的AppKey")
    @Transient
    private String appKey;

    @Description("邮箱验证码")
    @NotBlank(groups = {WhenRegister.class, WhenUpdateMyPassword.class, WhenResetMyPassword.class}, message = "邮箱验证码不能为空")
    @Transient
    private String code;

    @Description("原始密码")
    @NotBlank(groups = {WhenUpdateMyPassword.class}, message = "原始密码不能为空")
    @Transient
    private String oldPassword;

    /**
     * <h2>获取是否超级管理员</h2>
     *
     * @return 结果
     */
    public final boolean isRootUser() {
        return getId() == 1L;
    }
}
