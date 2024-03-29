package cn.hamm.demo.module.system.log;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.demo.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm
 */
@RestController
@RequestMapping("log")
@Description("日志")
public class LogController extends BaseController<LogEntity, LogService, LogRepository> {
}
