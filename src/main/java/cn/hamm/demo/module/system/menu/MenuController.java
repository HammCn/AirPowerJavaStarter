package cn.hamm.demo.module.system.menu;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.query.QueryRequest;
import cn.hamm.airpower.result.json.JsonData;
import cn.hamm.airpower.security.Permission;
import cn.hamm.airpower.util.TreeUtil;
import cn.hamm.demo.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm
 */
@RestController
@RequestMapping("menu")
@Description("菜单")
public class MenuController extends BaseController<MenuEntity, MenuService, MenuRepository> {
    @Autowired
    private TreeUtil treeUtil;

    @Permission(authorize = false)
    @Override
    public JsonData getList(@RequestBody QueryRequest<MenuEntity> queryRequest) {
        return jsonData(treeUtil.buildTreeList(service.getList(queryRequest)));
    }
}
