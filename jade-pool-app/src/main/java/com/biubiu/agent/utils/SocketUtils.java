package com.biubiu.agent.utils;

import javax.websocket.Session;
import java.io.IOException;

public class SocketUtils {

    public static void send(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
