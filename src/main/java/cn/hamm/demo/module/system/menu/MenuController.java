package cn.hamm.demo.module.system.menu;

import cn.hamm.airpower.core.annotation.ApiController;
import cn.hamm.airpower.core.annotation.Description;
import cn.hamm.airpower.core.annotation.Permission;
import cn.hamm.airpower.core.json.Json;
import cn.hamm.airpower.core.util.TreeUtil;
import cn.hamm.airpower.crud.model.query.QueryListRequest;
import cn.hamm.demo.base.BaseController;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm.cn
 */
@ApiController("menu")
@Description("菜单")
public class MenuController extends BaseController<MenuEntity, MenuService, MenuRepository> {
    @Permission(authorize = false)
    @Override
    public Json getList(@RequestBody QueryListRequest<MenuEntity> queryListRequest) {
        return Json.data(TreeUtil.buildTreeList(service.getList(queryListRequest)));
    }
}
