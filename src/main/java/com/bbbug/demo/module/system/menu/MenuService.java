package com.bbbug.demo.module.system.menu;

import cn.hamm.airpower.model.Sort;
import cn.hamm.airpower.query.QueryRequest;
import cn.hamm.airpower.result.Result;
import com.bbbug.demo.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <h1>Service</h1>
 *
 * @author Hamm
 */
@Service
public class MenuService extends BaseService<MenuEntity, MenuRepository> {
    @Override
    protected void beforeDelete(Long id) {
        QueryRequest<MenuEntity> queryRequest = new QueryRequest<>();
        queryRequest.setFilter(new MenuEntity().setParentId(id));
        List<MenuEntity> children = getList(queryRequest);
        Result.FORBIDDEN_DELETE.when(children.size() > 0, "含有子菜单,无法删除!");
    }

    @Override
    protected QueryRequest<MenuEntity> beforeGetList(QueryRequest<MenuEntity> queryRequest) {
        MenuEntity filter = queryRequest.getFilter();
        if (Objects.isNull(queryRequest.getSort())) {
            queryRequest.setSort(new Sort().setField("orderNo"));
        }
        if (Objects.isNull(filter.getParentId())) {
            filter.setParentId(0L);
        }
        return queryRequest.setFilter(filter);
    }

    @Override
    protected List<MenuEntity> afterGetList(List<MenuEntity> list) {
        for (MenuEntity item : list) {
            QueryRequest<MenuEntity> queryRequest = new QueryRequest<>();
            queryRequest.setFilter(new MenuEntity().setParentId(item.getId()));
            item.setChildren(this.getList(queryRequest));
            item.excludeBaseData();
        }
        return list;
    }
}
