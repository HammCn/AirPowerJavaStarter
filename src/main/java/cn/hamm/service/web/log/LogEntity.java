package cn.hamm.service.web.log;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.root.RootEntity;
import cn.hamm.service.web.user.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * <h1>日志实体</h1>
 *
 * @author Hamm
 */
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "log")
@Description("日志")
public class LogEntity extends RootEntity<LogEntity> {
    /**
     * <h1>日志动作</h1>
     */
    @Column(columnDefinition = "varchar(255) default '' comment '动作'")
    private String action;

    /**
     * <h1>IP地址</h1>
     */
    @Column(columnDefinition = "varchar(255) default '' comment 'IP地址'")
    private String ip;

    /**
     * <h1>客户端版本</h1>
     */
    @Column(columnDefinition = "varchar(8) default '000001' comment '客户端版本'")
    private String version;

    /**
     * <h1>日志体</h1>
     */
    @Column(columnDefinition = "text comment '日志体'")
    private String content;

    /**
     * <h1>扩展数据</h1>
     */
    @Column(columnDefinition = "text comment '扩展数据'")
    private String payload;

    /**
     * <h1>用户信息</h1>
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userInfo;
}
