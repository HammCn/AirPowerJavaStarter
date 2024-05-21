package cn.hamm.demo.module.supplier;

import cn.hamm.airpower.annotation.ApiController;
import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.Extends;
import cn.hamm.airpower.enums.Api;
import cn.hamm.airpower.model.Json;
import cn.hamm.demo.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm.cn
 */
@ApiController("supplier")
@Description("供应商")
@Extends(exclude = Api.Delete)
public class SupplierController extends BaseController<SupplierEntity, SupplierService, SupplierRepository> {
    @RequestMapping("test")
    public Json test() {
        return Json.success("ok");
    }
}
