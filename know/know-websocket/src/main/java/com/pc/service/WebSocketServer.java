package com.pc.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 服务端
 *
 * @author pc
 * @Date 2020/10/14
 **/
@Component
@ServerEndpoint("/websocket/{sid}")
public class WebSocketServer {

    static Log log = LogFactory.getLog(WebSocketServer.class);

    /**
     * 线程安全的统计在线数
     */
    public static AtomicInteger count = new AtomicInteger(0);

    /**
     * 保存连接的客户端
     */
    public static Map<String, Session> webSocketClients = new ConcurrentHashMap<>();

    private String sid = "";

    private Session session;

    /**
     * 客户端建立连接
     *
     * @param session
     * @param sid
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.sid = sid;
        this.session = session;
        //1、记录在线数
        int current = count.incrementAndGet();
        //2、保存连接的客户端
        webSocketClients.put(sid, session);
        log.info("有新窗口开始监听:" + sid + ",当前在线人数为" + current);
        //3、回复客户端消息
        sendMessage("连接成功");
    }

    /**
     * 客户端关闭连接
     */
    @OnClose
    public void onCloes() {
        if (webSocketClients.containsKey(sid)) {
            webSocketClients.remove(sid);
            int i = count.decrementAndGet();
            log.info("用户退出:" + sid + ",当前在线人数为:" + i);
        }
    }

    /**
     * 服务端收到消息后调用的方法
     *
     * @param session
     * @param message
     */
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        log.info("用户消息:" + this.sid + ",报文:" + message);
        //可以根据message的值判断是群发还是单发
        if (StringUtils.isNotEmpty(message)) {
            if (message.indexOf("group") == 0) {
                Collection<Session> values = webSocketClients.values();
                if (!CollectionUtils.isEmpty(values)) {
                    for (Session value : values) {
                        value.getBasicRemote().sendText("已收到");
                    }
                } else {
                    session.getBasicRemote().sendText("已收到");
                }
            }
        }
    }

    /**
     * 错误处理
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:" + this.sid + ",原因:" + error.getMessage());
    }

    /**
     * 发送消息至客户端
     *
     * @param message
     */
    private void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
