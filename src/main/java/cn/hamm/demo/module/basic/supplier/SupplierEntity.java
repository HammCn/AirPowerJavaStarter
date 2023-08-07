package cn.hamm.demo.module.basic.supplier;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.demo.base.BaseEntity;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * <h1>供应商实体</h1>
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
@Table(name = "supplier")
@Description("供应商")
public class SupplierEntity extends BaseEntity<SupplierEntity> {
    @Description("供应商编码")
    @NotNull(groups = {WhenUpdate.class, WhenAdd.class})
    @Column(columnDefinition = "varchar(255) default '' comment '供应商编码'", unique = true)
    private String code;

    @Description("供应商名称")
    @NotNull(groups = {WhenUpdate.class, WhenAdd.class})
    @Column(columnDefinition = "varchar(255) default '' comment '供应商名称'")
    private String name;

    @Description("供应商级别")
    @Column(columnDefinition = "int default 3 comment '供应商级别'")
    @Min(groups = {WhenUpdate.class, WhenAdd.class}, value = 0)
    @Max(groups = {WhenUpdate.class, WhenAdd.class}, value = 100)
    @NotNull(groups = {WhenUpdate.class, WhenAdd.class})
    private Integer level;

    @Description("手机号")
    @Column(columnDefinition = "varchar(255) default '' comment '手机号'")
    private String phone;

    @Description("供应能力指标")
    @Column(columnDefinition = "int default 0 comment '供应能力指标'")
    private Integer supplyAvg;
}
