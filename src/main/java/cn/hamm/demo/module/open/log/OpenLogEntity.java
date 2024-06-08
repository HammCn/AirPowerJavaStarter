package cn.hamm.demo.module.open.log;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.ReadOnly;
import cn.hamm.demo.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "open_log")
@Description("调用日志")
public class OpenLogEntity extends BaseEntity<OpenLogEntity> implements IOpenLogAction {
    @Description("AppKey")
    @Column(columnDefinition = "varchar(255) default '' comment 'AppKey'")
    @ReadOnly
    private String appKey;

    @Description("URL")
    @Column(columnDefinition = "varchar(255) default '' comment 'URL'")
    @ReadOnly
    private String url;

    @Description("请求")
    @Column(columnDefinition = "text comment '请求'")
    @ReadOnly
    private String request;

    @Description("响应")
    @Column(columnDefinition = "text comment '响应'")
    @ReadOnly
    private String response;

    @Description("请求时间")
    @Column(columnDefinition = "bigint UNSIGNED default 0 comment '请求时间'")
    @ReadOnly
    private Long requestStartTime;

    @Description("响应时间")
    @Column(columnDefinition = "bigint UNSIGNED default 0 comment '响应时间'")
    @ReadOnly
    private Long requestFinishTime;
}
