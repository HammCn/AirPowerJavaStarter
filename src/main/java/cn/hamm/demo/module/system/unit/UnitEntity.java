package cn.hamm.demo.module.system.unit;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.Search;
import cn.hamm.demo.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * <h1>单位实体</h1>
 *
 * @author Hamm.cn
 */
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "unit")
@Description("单位")
public class UnitEntity extends BaseEntity<UnitEntity> {
    @Description("单位名称")
    @Search(Search.Mode.EQUALS)
    @Column(columnDefinition = "varchar(255) default '' comment '单位名称'", unique = true)
    private String name;

    @Description("单位编码")
    @Column(columnDefinition = "varchar(255) default '' comment '单位编码'", unique = true)
    @NotNull(groups = {WhenUpdate.class, WhenAdd.class}, message = "编码不能为空")
    private String code;
}
