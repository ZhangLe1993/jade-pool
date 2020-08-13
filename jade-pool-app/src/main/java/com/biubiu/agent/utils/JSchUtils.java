package com.biubiu.agent.utils;

import com.biubiu.agent.model.Remote;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSchUtils {

    /**
     * 获取远程连接Session
     * @param remote
     * @return
     * @throws JSchException
     */
    public static Session getSession(Remote remote) throws JSchException {
        JSch jSch = new JSch();
        if (Files.exists(Paths.get(remote.getIdentity()))) {
            jSch.addIdentity(remote.getIdentity(), remote.getPassphrase());
        }
        Session session = jSch.getSession(remote.getUser(), remote.getHost(),remote.getPort());
        session.setPassword(remote.getPassword());
        session.setConfig("StrictHostKeyChecking", "no");
        // 跳过Kerberos身份验证
        session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
        return session;
    }

    public static InputStream openShell(Session session) throws JSchException, IOException {
        Channel channel = session.openChannel("shell");
        return channel.getInputStream();
    }

    public static void remoteResponse(InputStream inputStream) throws IOException {
        // 新建缓冲区
        byte[] buff = new byte[10240];
        // 阻塞接收,前端不需要输入显示太多，全部由后台接收就行
        int readLen = inputStream.read(buff);
        if (readLen != -1) {
            ByteBuffer byteBuffer = ByteBuffer.wrap(buff, 0, readLen);
            byte[] readData = new byte[readLen];
            byteBuffer.position(0);
            byteBuffer.get(readData);
            String data = new String(readData, StandardCharsets.UTF_8);
            sendMessage(data);
        }
    }

    public static void sendMessage(String data) {

    }

    public static void main(String[] args) throws Exception {
        Remote remote = new Remote();
        // remote.setHost("192.168.124.20");
        // remote.setPassword("123456");
        Session session = getSession(remote);
        session.connect(3000);
        if (session.isConnected()) {
            System.out.println("Host({" + remote.getHost() + "}) connected.");
        }
        session.disconnect();
    }
}
