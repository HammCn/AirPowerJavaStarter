package cn.hamm.demo.module.customer;

import cn.hamm.airpower.annotation.ApiController;
import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.Extends;
import cn.hamm.airpower.enums.Api;
import cn.hamm.demo.base.BaseController;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm.cn
 */
@ApiController("customer")
@Description("客户")
@Extends(exclude = Api.Delete)
public class CustomerController extends BaseController<CustomerEntity, CustomerService, CustomerRepository> {
}
