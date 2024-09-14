package cn.hamm.demo.module.system.permission;

import cn.hamm.airpower.annotation.ApiController;
import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.Permission;
import cn.hamm.airpower.model.Json;
import cn.hamm.airpower.model.query.QueryListRequest;
import cn.hamm.airpower.model.query.QueryRequest;
import cn.hamm.airpower.util.Utils;
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
    public Json getList(QueryListRequest<PermissionEntity> queryListRequest) {
        return Json.data(Utils.getTreeUtil().buildTreeList(service.getList(queryListRequest)));
    }
}
