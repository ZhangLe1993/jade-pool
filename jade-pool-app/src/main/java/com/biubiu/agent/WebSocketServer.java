package com.biubiu.agent;

import com.alibaba.fastjson.JSON;
import com.biubiu.agent.model.MessageModel;
import com.biubiu.agent.model.Remote;
import com.biubiu.agent.model.SshBuilder;
import com.biubiu.agent.utils.SocketSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Slf4j
@ServerEndpoint("/terminal")
@Component
public class WebSocketServer {

    private SshBuilder sshBuilder;

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收sid
    private String sid = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        String sid = session.getId();
        if(!SocketSessionManager.contains(sid)) {
            SocketSessionManager.add(sid, session);
        }
        this.sid = sid;
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        /*if(!SocketSessionManager.contains(sid)) {
            SocketSessionManager.add(sid, session);
        }
        SocketSessionManager.remove(sid);*/
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("message: {}", message);
        MessageModel messageModel = JSON.parseObject(message, MessageModel.class);
        switch(messageModel.getType()) {
            case "connect":
                Remote remote = JSON.parseObject(messageModel.getData(), Remote.class);
                if(null == this.sshBuilder) {
                    this.sshBuilder = new SshBuilder(remote);
                    try {
                        sshBuilder.start(session);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "command":
                log.info("command: {}", messageModel.getData());
                String cmd = messageModel.getData();
                this.sshBuilder.exec(cmd);
            default:
                break;
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     *
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


}
