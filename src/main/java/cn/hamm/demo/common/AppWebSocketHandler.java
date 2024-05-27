package cn.hamm.demo.common;

import cn.hamm.airpower.websocket.WebSocketHandler;
import cn.hamm.airpower.websocket.WebSocketPayload;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * <h1>应用自定义的事件处理器</h1>
 *
 * @author Hamm.cn
 */
@Slf4j
@Component
public class AppWebSocketHandler extends WebSocketHandler {
    @Override
    public void onWebSocketPayload(@NotNull WebSocketPayload webSocketPayload) {
        super.onWebSocketPayload(webSocketPayload);
    }
}
