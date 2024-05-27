package cn.hamm.demo;

import cn.hamm.airpower.AbstractWebConfig;
import cn.hamm.airpower.interceptor.AbstractRequestInterceptor;
import cn.hamm.airpower.websocket.WebSocketHandler;
import cn.hamm.demo.common.AppWebSocketHandler;
import cn.hamm.demo.common.interceptor.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>全局配置</h1>
 *
 * @author Hamm.cn
 */
@Configuration
public class WebConfig extends AbstractWebConfig {
    @Autowired
    private RequestInterceptor requestInterceptor;

    @Autowired
    private AppWebSocketHandler appWebSocketHandler;

    @Override
    public WebSocketHandler getWebsocketHandler() {
        return appWebSocketHandler;
    }

    @Override
    public AbstractRequestInterceptor getAccessInterceptor() {
        return requestInterceptor;
    }
}