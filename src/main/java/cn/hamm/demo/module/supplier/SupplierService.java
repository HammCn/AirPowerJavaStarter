package cn.hamm.demo.module.supplier;

import cn.hamm.demo.base.BaseService;
import cn.hamm.demo.module.notify.NotifyService;
import cn.hamm.demo.module.notify.enums.NotifyScene;
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
    private NotifyService notifyService;

    @Override
    protected void afterAdd(long id, @NotNull SupplierEntity supplier) {
        notifyService.sendNotification(NotifyScene.SUPPLIER_ADD, get(id), String.format("创建了供应商 (%s)",
                supplier.getName()
        ));
    }

    @Override
    protected void afterUpdate(long id, @NotNull SupplierEntity supplier) {
        notifyService.sendNotification(NotifyScene.SUPPLIER_UPDATE, get(id), String.format("修改了供应商 (%s)",
                supplier.getName()
        ));
    }
}