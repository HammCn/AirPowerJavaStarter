package cn.hamm.demo.module.supplier;

import cn.hamm.demo.base.BaseService;
import cn.hamm.demo.module.webhook.WebHookService;
import cn.hamm.demo.module.webhook.enums.WebHookScene;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <h1>Service</h1>
 *
 * @author Hamm.cn
 */
@Service
public class SupplierService extends BaseService<SupplierEntity, SupplierRepository> {
    @Autowired
    private WebHookService webHookService;

    @Override
    protected void afterAdd(long id, @NotNull SupplierEntity supplierEntity) {
        webHookService.sendHook(WebHookScene.SUPPLIER_ADD, get(id));
    }

    @Override
    protected void afterUpdate(long id, @NotNull SupplierEntity supplierEntity) {
        webHookService.sendHook(WebHookScene.SUPPLIER_UPDATE, get(id));
    }
}