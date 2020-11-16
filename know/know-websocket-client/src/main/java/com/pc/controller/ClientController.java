package com.pc.controller;

import com.pc.redis.MyWebSocketClient;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft_6455;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.net.URI;

/**
 * 测试平台
 * @author pc
 * @Date 2020/10/14
 **/
@RequestMapping("/test")
@RestController
public class ClientController {

    private MyWebSocketClient client1;

    private MyWebSocketClient client2;

    /**
     * 初始化两个客户端
     */
    @PostConstruct
    public void initClients() {
        try {
            client1 = new MyWebSocketClient(new URI("ws://127.0.0.1:8080/websocket/pc"), new Draft_6455());
            client2 = new MyWebSocketClient(new URI("ws://127.0.0.1:8080/websocket/lyf"), new Draft_6455());
            //阻塞直到成功
            client1.connect();
            client2.connect();
            while (!client1.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                System.out.println("client1 正常连接中....");
            }
            while (!client2.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                System.out.println("client2  正常连接中....");
            }
            System.out.println("成功建立连接!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("client1")
    public String client1SendMsg(String message) {
        client1.send(message);
        return "发送成功";
    }

    @GetMapping("client2")
    public String client2SendMsg(String message) {
        client2.send(message);
        return "发送成功";
    }

}
