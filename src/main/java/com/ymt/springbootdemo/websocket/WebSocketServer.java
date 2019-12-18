package com.ymt.springbootdemo.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by @author yangmingtian on 2019/12/18
 */
@ServerEndpoint(value = "/ws/asset")
@Component
public class WebSocketServer {


    @PostConstruct
    public void init() {
        System.out.println("websocket 加载");
    }

    private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);

    private static CopyOnWriteArraySet<Session> sessionSet = new CopyOnWriteArraySet<Session>();


    @OnOpen
    public void onopen(Session session) {
        sessionSet.add(session);
        int cnt = ONLINE_COUNT.incrementAndGet();
        log.info("有连接，当前连接数：{}", cnt);
        sendMessage(session, "连接成功");
    }

    @OnClose
    public void onClose(Session session) {
        sessionSet.remove(session);
        int cnt = ONLINE_COUNT.decrementAndGet();
        log.info("有连接关闭，当前连接数为：{}", cnt);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息：{}", message);
        sendMessage(session, "收到消息: " + message);

    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误：{},session ID: {}", error.getMessage(), session.getId());
        error.printStackTrace();
    }

    private static void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("发送消息出错：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void broadCastInfo(String message) {
        for (Session session : sessionSet) {
            if (session.isOpen()) {
                sendMessage(session, message);
            }
        }
    }

    /**
     * 指定Session发送消息
     *
     * @param sessionId
     * @param message
     * @throws IOException
     */
    public static void sendMessage(String message, String sessionId) throws IOException {
        Session session = null;
        for (Session s : sessionSet) {
            if (s.getId().equals(sessionId)) {
                session = s;
                break;
            }
        }
        if (session != null) {
            sendMessage(session, message);
        } else {
            log.warn("没有找到你指定ID的会话：{}", sessionId);
        }
    }
}
