package cn.hamm.demo.module.system.permission;

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
@ApiController("permission")
@Description("权限")
public class PermissionController extends BaseController<PermissionEntity, PermissionService, PermissionRepository> {
    @Permission(authorize = false)
    @Override
    public Json getList(@RequestBody QueryListRequest<PermissionEntity> queryListRequest) {
        return Json.data(TreeUtil.buildTreeList(service.getList(queryListRequest)));
    }
}
