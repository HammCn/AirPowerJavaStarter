package cn.hamm.demo.common.interceptor;

import cn.hamm.airpower.interceptor.ResponseBodyInterceptor;
import cn.hamm.airpower.model.Json;
import cn.hamm.demo.common.Services;
import cn.hamm.demo.module.system.log.LogEntity;
import cn.hamm.demo.module.system.log.LogService;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * <h1>响应拦截器</h1>
 *
 * @author Hamm.cn
 */
@Component
public class ResponseInterceptor extends ResponseBodyInterceptor {
    @Override
    protected Object beforeResponseFinished(Object body, ServerHttpRequest request, ServerHttpResponse response) {
        Object logId = getShareData(RequestInterceptor.LOG_ID);
        if (Objects.isNull(logId)) {
            return body;
        }
        LogService logService = Services.getLogService();
        LogEntity log = logService.getMaybeNull(Long.parseLong(logId.toString()));
        if (Objects.isNull(log)) {
            return null;
        }
        String responseBody = body.toString();
        try {
            responseBody = Json.toString(body);
        } catch (Exception ignored) {

        }
        log.setResponse(responseBody);
        logService.update(log);
        return body;
    }
}
