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
    protected <T extends QueryPageRequest<OpenLogEntity>> T beforeGetPage(T queryPageRequest) {
        OpenAppEntity openApp = new OpenAppEntity();
        openApp.setOwner(Services.getUserService().get(getCurrentUserId()));
        queryPageRequest.setFilter(queryPageRequest.getFilter().setOpenApp(openApp));
        return queryPageRequest;
    }

    @Override
    protected OpenLogEntity afterGetDetail(OpenLogEntity openLog) {
        ServiceError.DATA_NOT_FOUND.whenNotEquals(openLog.getOpenApp().getOwner().getId(), getCurrentUserId(), "没有查询到日志信息");
        return openLog;
    }
}
