package cn.hamm.demo.module.user.thirdlogin;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.Search;
import cn.hamm.demo.base.BaseEntity;
import cn.hamm.demo.module.user.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * <h1>实体</h1>
 *
 * @author Hamm.cn
 */
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "user_third_login")
@Description("调用日志")
public class UserThirdLoginEntity extends BaseEntity<UserThirdLoginEntity> {
    @Description("userId")
    @Column(columnDefinition = "varchar(255) default '' comment 'userId'")
    private String thirdUserId;

    @Description("头像")
    @Column(columnDefinition = "varchar(255) default '' comment '头像'")
    private String avatar;

    @Description("昵称")
    @Column(columnDefinition = "varchar(255) default '' comment '昵称'")
    private String nickName;

    @Description("性别")
    @Column(columnDefinition = "tinyint UNSIGNED default 0 comment '性别'")
    @Search(Search.Mode.EQUALS)
    private Integer gender;

    @Description("所属平台")
    @Column(columnDefinition = "tinyint UNSIGNED default 0 comment '所属平台'")
    @Search(Search.Mode.EQUALS)
    private Integer platform;

    @Description("用户")
    @ManyToOne
    @Search(Search.Mode.JOIN)
    private UserEntity user;
}
