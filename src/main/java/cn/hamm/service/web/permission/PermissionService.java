package cn.hamm.service.web.permission;

import cn.hamm.airpower.query.QueryRequest;
import cn.hamm.airpower.result.Result;
import cn.hamm.airpower.root.RootService;
import cn.hamm.airpower.util.ReflectUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <h1>Service</h1>
 *
 * @author Hamm
 */
@Service
public class PermissionService extends RootService<PermissionEntity, PermissionRepository> {
    /**
     * <h1>通过标识获取一个权限</h1>
     *
     * @param identity 权限标识
     * @return 权限
     */
    public PermissionEntity getPermissionByIdentity(String identity) {
        return repository.getByIdentity(identity);
    }

    /**
     * <h1>强制重载所有权限</h1>
     */
    public void forceReloadAllPermissions() {
        redisUtil.clearAll();
        List<Map<String, Object>> appModuleList = ReflectUtil.getApiTreeList("cn.hamm");
        saveModuleApiToDatabase(appModuleList);
    }

    /**
     * <h1>保存所有API到权限表</h1>
     *
     * @param moduleList moduleList
     */
    private void saveModuleApiToDatabase(List<Map<String, Object>> moduleList) {
        for (Map<String, Object> module : moduleList) {
            List<Map<String, Object>> apis = (List<Map<String, Object>>) module.get("apis");
            String controllerName = module.get("module").toString().replaceAll("Controller", "");
            PermissionEntity modulePermission = new PermissionEntity()
                    .setName(module.get("name").toString())
                    .setIdentity(controllerName)
                    .setIsSystem(true)
                    .setRemark("");
            modulePermission = add(modulePermission);
            for (Map<String, Object> api : apis) {
                String identity = api.get("path").toString().replaceAll("/", "_");
                PermissionEntity apiPermission = new PermissionEntity()
                        .setIdentity(identity)
                        .setParentId(modulePermission.getId())
                        .setName(module.get("name").toString() + "_" + api.get("name").toString())
                        .setIsSystem(true)
                        .setRemark("");
                try {
                    add(apiPermission);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void beforeDelete(PermissionEntity entity) {
        Result.FORBIDDEN_DELETE.when(entity.getIsSystem(), "系统内置权限无法被删除!");
        QueryRequest<PermissionEntity> queryRequest = new QueryRequest<>();
        queryRequest.setFilter(new PermissionEntity().setParentId(entity.getId()));
        List<PermissionEntity> children = getList(queryRequest);
        Result.FORBIDDEN_DELETE.when(children.size() > 0, "含有子权限,无法删除!");
    }

    @Override
    protected QueryRequest<PermissionEntity> beforeGetList(QueryRequest<PermissionEntity> queryRequest) {
        PermissionEntity filter = queryRequest.getFilter();
        if (Objects.isNull(filter.getParentId())) {
            filter.setParentId(0L);
        }
        return queryRequest.setFilter(filter);
    }

    @Override
    protected List<PermissionEntity> afterGetList(List<PermissionEntity> list) {
        for (PermissionEntity item : list) {
            QueryRequest<PermissionEntity> queryRequest = new QueryRequest<>();
            queryRequest.setFilter(new PermissionEntity().setParentId(item.getId()));
            item.setChildren(this.getList(queryRequest));
            item.setCreateTime(null).setUpdateTime(null).setCreateUserId(null).setUpdateUserId(null).setRemark(null).setIsDisabled(null);
        }
        return list;
    }
}
