package com.pc.controller;

import com.pc.service.WebSocketServer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.Session;
import java.util.Collection;

/**
 * 服务端控制台
 * @author pc
 * @Date 2020/10/14
 **/
@RequestMapping("server")
@RestController
public class ServerController {

    /**
     * 服务端直接发送消息至连接的服务端
     *
     * 当然这里是发送给在线的用户，如果需要发送给离线的用户，可以将sid与消息保存在数据中，当这个
     *  客户端上线的时候，可以从数据库中取出相应的数据继续发送
     * @param sid
     * @param message
     * @return
     */
    @RequestMapping("sendMessage")
    public String sendMessage(String sid, String message) {
        try {
            if (StringUtils.isNotEmpty(sid)) {
                Session session = WebSocketServer.webSocketClients.get(sid);
                if (session != null) {
                    session.getBasicRemote().sendText(message);
                }
            } else {
                Collection<Session> values = WebSocketServer.webSocketClients.values();
                if (CollectionUtils.isNotEmpty(values)) {
                    for (Session value : values) {
                        value.getBasicRemote().sendText(message);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "发送成功";
    }

}
