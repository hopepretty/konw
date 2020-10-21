package com.pc.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Iterator;

/**
 * 客戶端
 *
 * @author pc
 * @Date 2020/10/14
 **/
public class MyWebSocketClient extends WebSocketClient {

    static Log log = LogFactory.getLog(MyWebSocketClient.class);

    public MyWebSocketClient(URI serverUri, Draft protocolDraft) {
        super(serverUri, protocolDraft);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("开始建立服务端连接......");
        for (Iterator<String> it = serverHandshake.iterateHttpFields(); it.hasNext(); ) {
            String key = it.next();
            System.out.println(key + ":" + serverHandshake.getFieldValue(key));
        }
    }

    @Override
    public void onMessage(String s) {
        log.info("接收到服务端发送的消息：" + s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        log.info("已断开连接：" + s);
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }
}
