package cn.hamm.demo.common;

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
    /**
     * <h3>订阅分组前缀</h3>
     */
    public static final String GROUP_PREFIX = "group_";

    /**
     * <h3>加入房间</h3>
     */
    private static final String EVENT_JOIN = "join";

    /**
     * <h3>离开房间</h3>
     */
    private static final String EVENT_LEAVE = "leave";

    @Override
    public void onWebSocketPayload(@NotNull WebSocketPayload webSocketPayload, @NotNull WebSocketSession session) {
        switch (webSocketPayload.getType()) {
            case EVENT_JOIN:
                subscribe(GROUP_PREFIX + webSocketPayload.getData(), session);
                sendWebSocketPayload(session, new WebSocketPayload().setType(EVENT_JOIN).setData("User " + userIdHashMap.get(session.getId()) + " 加入了房间"));
                break;
            case EVENT_LEAVE:
                unsubscribe(GROUP_PREFIX + webSocketPayload.getData(), session);
                break;
            default:
        }
    }
}
