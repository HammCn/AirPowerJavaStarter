package cn.hamm.demo.module.system.permission;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.query.QueryRequest;
import cn.hamm.airpower.result.json.JsonData;
import cn.hamm.airpower.util.TreeUtil;
import cn.hamm.demo.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm.cn
 */
@RestController
@RequestMapping("permission")
@Description("权限")
public class PermissionController extends BaseController<PermissionEntity, PermissionService, PermissionRepository> {
    @Autowired
    private TreeUtil treeUtil;

    @Override
    public JsonData getList(@RequestBody QueryRequest<PermissionEntity> queryRequest) {
        return jsonData(treeUtil.buildTreeList(service.getList(queryRequest)));
    }
}
