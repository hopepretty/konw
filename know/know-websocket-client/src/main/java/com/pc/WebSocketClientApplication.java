package com.pc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * websocket服务端
 * @author pc
 * @Date 2020/10/14
 **/
@SpringBootApplication
public class WebSocketClientApplication {

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(WebSocketClientApplication.class, args);
//        try {
//            MyWebSocketClient client = new MyWebSocketClient(new URI("ws://127.0.0.1:8080/websocket/22"), new Draft_6455());
//            client.connect();
//            //验证是否建立连接
//            while (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
//                System.out.println("未建立连接，正在尝试....");
//            }
//            System.out.println("成功建立连接!");
//            client.send("你好啊");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}

