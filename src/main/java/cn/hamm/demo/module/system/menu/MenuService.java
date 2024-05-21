package cn.hamm.demo.module.system.menu;

import cn.hamm.airpower.enums.ServiceError;
import cn.hamm.airpower.model.Sort;
import cn.hamm.airpower.model.query.QueryRequest;
import cn.hamm.airpower.root.RootEntity;
import cn.hamm.demo.base.BaseService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <h1>Service</h1>
 *
 * @author Hamm.cn
 */
@Service
public class MenuService extends BaseService<MenuEntity, MenuRepository> {
    /**
     * <h2>排序字段</h2>
     */
    private static final String ORDER_FIELD_NAME = "orderNo";

    @Override
    protected void beforeDelete(long id) {
        QueryRequest<MenuEntity> queryRequest = new QueryRequest<>();
        queryRequest.setFilter(new MenuEntity().setParentId(id));
        List<MenuEntity> children = getList(queryRequest);
        ServiceError.FORBIDDEN_DELETE.when(!children.isEmpty(), "含有子菜单,无法删除!");
    }

    @Override
    protected <T extends QueryRequest<MenuEntity>> @NotNull T beforeGetList(@NotNull T sourceRequestData) {
        MenuEntity filter = sourceRequestData.getFilter();
        sourceRequestData.setSort(Objects.requireNonNullElse(
                sourceRequestData.getSort(),
                new Sort().setField(ORDER_FIELD_NAME)
        ));
        sourceRequestData.setFilter(filter);
        return sourceRequestData;
    }

    @Override
    protected @NotNull List<MenuEntity> afterGetList(@NotNull List<MenuEntity> list) {
        list.forEach(RootEntity::excludeBaseData);
        return list;
    }


    public final void loadMenu() {
        MenuEntity exist = getMaybeNull(1L);
        if (Objects.nonNull(exist)) {
            return;
        }
        MenuEntity homeMenu = new MenuEntity().setName("首页").setOrderNo(99).setPath("/console").setComponent("/console/index/index").setParentId(0L);
        add(homeMenu);

        // 人事管理
        MenuEntity userMenu = new MenuEntity().setName("人事管理").setOrderNo(88).setParentId(0L);
        userMenu = get(add(userMenu));

        MenuEntity userSubMenu;
        userSubMenu = new MenuEntity().setName("用户管理").setPath("/console/user/list").setParentId(userMenu.getId());
        add(userSubMenu);

        userSubMenu = new MenuEntity().setName("角色管理").setPath("/console/role/list").setParentId(userMenu.getId());
        add(userSubMenu);

        // 渠道管理
        MenuEntity sourceMenu = new MenuEntity().setName("渠道管理").setOrderNo(77).setParentId(0L);
        sourceMenu = get(add(sourceMenu));

        MenuEntity sourceSubMenu;

        sourceSubMenu = new MenuEntity().setName("供应商管理").setPath("/console/supplier/list").setParentId(sourceMenu.getId());
        add(sourceSubMenu);

        // 系统设置
        MenuEntity sysMenu = new MenuEntity().setName("系统设置").setOrderNo(2).setParentId(0L);
        sysMenu = get(add(sysMenu));

        MenuEntity sysSubMenu;

        sysSubMenu = new MenuEntity().setName("权限管理").setPath("/console/permission/list").setParentId(sysMenu.getId());
        add(sysSubMenu);

        sysSubMenu = new MenuEntity().setName("菜单管理").setPath("/console/menu/list").setParentId(sysMenu.getId());
        add(sysSubMenu);

        sysSubMenu = new MenuEntity().setName("第三方应用").setPath("/console/app/list").setParentId(sysMenu.getId());
        add(sysSubMenu);
    }

}
