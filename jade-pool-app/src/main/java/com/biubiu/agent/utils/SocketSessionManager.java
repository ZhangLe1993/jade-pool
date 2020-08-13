package com.biubiu.agent.utils;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SocketSessionManager {

    private static ConcurrentHashMap<String, Session> manager = new ConcurrentHashMap<String, Session>();

    public static void add(String key, Session session) {
        log.info("新添加webSocket连接 {} ", key);
        manager.put(key, session);
    }

    public static void remove(String key) {
        log.info("移除webSocket连接 {} ", key);
        manager.remove(key);
    }

    public static boolean contains(String key) {
        return manager.containsKey(key);
    }

    public static Session get(String key) {
        log.info("获取webSocket连接 {}", key);
        return manager.get(key);
    }

}
