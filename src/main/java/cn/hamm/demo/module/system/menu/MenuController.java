package cn.hamm.demo.module.system.menu;

import cn.hamm.airpower.annotation.ApiController;
import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.Permission;
import cn.hamm.airpower.model.Json;
import cn.hamm.airpower.model.query.QueryListRequest;
import cn.hamm.airpower.util.TreeUtil;
import cn.hamm.demo.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm.cn
 */
@ApiController("menu")
@Description("菜单")
public class MenuController extends BaseController<MenuEntity, MenuService, MenuRepository> {
    @Autowired
    private TreeUtil treeUtil;

    @Permission(authorize = false)
    @Override
    public Json getList(@RequestBody QueryListRequest<MenuEntity> queryListRequest) {
        return Json.data(treeUtil.buildTreeList(service.getList(queryListRequest)));
    }
}
