package cn.hamm.demo.common;

import cn.hamm.airpower.util.Utils;
import cn.hamm.airpower.websocket.WebSocketMessage;

public class MessageUtil {
    public void send(String type, Object message) {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setType("register");
        webSocketMessage.setPayload(message);
        Utils.getWebsocketUtil().sendToAll(webSocketMessage);
        Utils.getWebsocketUtil().sendToUser(1L, webSocketMessage);
    }
}
