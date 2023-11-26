package com.bbbug.demo.module.customer;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.api.Api;
import cn.hamm.airpower.api.Extends;
import cn.hamm.airpower.security.Permission;
import com.bbbug.demo.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm
 */
@RestController
@RequestMapping("customer")
@Description("客户")
@Permission(login = false)
@Extends(exclude = Api.Delete)
public class CustomerController extends BaseController<CustomerEntity, CustomerService, CustomerRepository> {
}
