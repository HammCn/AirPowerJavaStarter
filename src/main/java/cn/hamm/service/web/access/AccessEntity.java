package cn.hamm.service.web.access;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.root.RootEntity;
import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <h1>授权信息实体</h1>
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
@Table(name = "access")
@Description("授权信息")
public class AccessEntity extends RootEntity<AccessEntity> {
    /**
     * <h1>授权类型</h1>
     *
     * @see AccessType
     */
    @Description("授权类型")
    @Column(columnDefinition = "tinyint UNSIGNED default 0 comment '授权类型'")
    private Integer accessType;

    /**
     * <h1>授权过期时间</h1>
     */
    @Description("过期时间")
    @Column(columnDefinition = "int UNSIGNED default 0 comment '过期时间'")
    private Integer expireTime;

    /**
     * <h1>权限ID</h1>
     */
    @Column(nullable = false, columnDefinition = "bigint UNSIGNED default 0 comment '权限ID'")
    private Long permissionId;

    /**
     * <h1>授权目标</h1>
     */
    @Column(nullable = false, columnDefinition = "bigint UNSIGNED default 0 comment '授权目标'")
    private Long accessTo;

    /**
     * <h1>判断授权是否已经过期</h1>
     *
     * @return 是否过期
     */
    public boolean isAccessExpired() {
        return this.getExpireTime() <= 0 || this.getExpireTime() >= DateUtil.current();
    }
}
