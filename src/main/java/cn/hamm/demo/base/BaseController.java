package cn.hamm.demo.base;

import cn.hamm.airpower.root.RootEntityController;
import cn.hamm.airpower.security.Permission;

/**
 * <h1>Controller</h1>
 *
 * @param <S> Service
 * @param <E> 实体
 * @param <R> 数据源
 * @author Hamm.cn
 */
@Permission
public class BaseController<E extends BaseEntity<E>, S extends BaseService<E, R>, R extends BaseRepository<E>> extends RootEntityController<E, S, R> {

}