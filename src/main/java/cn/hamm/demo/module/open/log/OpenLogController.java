package cn.hamm.demo.module.open.log;

import cn.hamm.airpower.annotation.ApiController;
import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.Extends;
import cn.hamm.airpower.enums.Api;
import cn.hamm.airpower.enums.ServiceError;
import cn.hamm.airpower.model.query.QueryPageRequest;
import cn.hamm.demo.base.BaseController;
import cn.hamm.demo.common.Services;
import cn.hamm.demo.module.open.app.OpenAppEntity;
import org.jetbrains.annotations.NotNull;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm.cn
 */
@ApiController("openLog")
@Description("调用日志")
@Extends({Api.GetDetail, Api.GetPage})
public class OpenLogController extends BaseController<OpenLogEntity, OpenLogService, OpenLogRepository> implements IOpenLogAction {
    @Override
    protected QueryPageRequest<OpenLogEntity> beforeGetPage(QueryPageRequest<OpenLogEntity> queryPageRequest) {
        OpenAppEntity openApp = new OpenAppEntity();
        openApp.setOwner(Services.getUserService().get(getCurrentUserId()));
        return queryPageRequest.setFilter(queryPageRequest.getFilter().setOpenApp(openApp));
    }

    @Override
    protected OpenLogEntity afterGetDetail(@NotNull OpenLogEntity openLog) {
        ServiceError.DATA_NOT_FOUND.whenNotEquals(openLog.getOpenApp().getOwner().getId(), getCurrentUserId(), "没有查询到日志信息");
        return openLog;
    }
}
