package cn.hamm.demo.base;

import cn.hamm.airpower.root.RootRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * <h1>Repository</h1>
 *
 * @param <E> 实体
 * @author Hamm.cn
 */
@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity<E>> extends RootRepository<E> {
}
