package cn.hamm.demo.base;

import cn.hamm.airpower.root.RootService;

/**
 * <h1>Service</h1>
 *
 * @param <E> 实体
 * @param <R> 数据源
 * @author Hamm.cn
 */
public class BaseService<E extends BaseEntity<E>, R extends BaseRepository<E>> extends RootService<E, R> {
}
