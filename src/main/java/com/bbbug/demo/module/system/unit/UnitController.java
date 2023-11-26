package com.bbbug.demo.module.system.unit;

import cn.hamm.airpower.annotation.Description;
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
@RequestMapping("unit")
@Description("单位")
@Permission(login = false)
public class UnitController extends BaseController<UnitEntity, UnitService, UnitRepository> {
}
