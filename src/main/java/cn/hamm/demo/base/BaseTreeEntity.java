package cn.hamm.demo.base;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.Search;
import cn.hamm.airpower.config.Constant;
import cn.hamm.airpower.interfaces.ITree;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * <h1>Tree Entity</h1>
 *
 * @author Hamm.cn
 */
@MappedSuperclass
@Getter
@Description("")
@SuppressWarnings("unchecked")
public class BaseTreeEntity<E extends BaseTreeEntity<E>> extends BaseEntity<E> implements ITree<E> {
    @Description("名称")
    @Search
    @Column(columnDefinition = "varchar(255) default '' comment '名称'")
    @Length(max = 200, message = "名称最多允许{max}个字符")
    @NotBlank(groups = {WhenUpdate.class, WhenAdd.class}, message = "名称不能为空")
    private String name;

    @Description("父级ID")
    @Column(columnDefinition = "bigint UNSIGNED default 0 comment '父级ID'")
    @Search(Search.Mode.EQUALS)
    private Long parentId;

    @Description("树子集节点数组")
    @Transient
    private List<E> children;

    @Override
    public E setName(String name) {
        this.name = name;
        return (E) this;
    }

    @Override
    public E setParentId(Long parentId) {
        this.parentId = parentId;
        return (E) this;
    }

    @Override
    public E setChildren(List<E> children) {
        this.children = children;
        return (E) this;
    }

    public E setRootTree() {
        parentId = Constant.ZERO_LONG;
        return (E) this;
    }
}
