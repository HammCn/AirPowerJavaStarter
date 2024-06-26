package cn.hamm.demo.module.system.log;

import cn.hamm.airpower.annotation.ApiController;
import cn.hamm.airpower.annotation.Description;
import cn.hamm.demo.base.BaseController;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm.cn
 */
@ApiController("log")
@Description("日志")
public class LogController extends BaseController<LogEntity, LogService, LogRepository> {
}
