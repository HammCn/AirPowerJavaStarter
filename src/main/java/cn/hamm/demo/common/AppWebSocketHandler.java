package cn.hamm.demo.common;

import cn.hamm.airpower.util.Utils;
import cn.hamm.airpower.websocket.WebSocketHandler;
import cn.hamm.airpower.websocket.WebSocketPayload;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

/**
 * <h1>应用自定义的事件处理器</h1>
 *
 * @author Hamm.cn
 */
@Slf4j
@Component
public class AppWebSocketHandler extends WebSocketHandler {
    @Override
    public void onWebSocketPayload(@NotNull WebSocketPayload webSocketPayload, @NotNull WebSocketSession session) {
        super.onWebSocketPayload(webSocketPayload, session);
        Utils.getWebsocketUtil().publish(webSocketPayload);
    }
}
