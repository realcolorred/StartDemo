package com.example.demo.component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.example.common.util.StringUtil;
import com.example.demo.bo.SocketMessage;
import com.example.demo.bo.SocketUserInfo;
import com.example.demo.service.ISocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by new on 2021/1/26.
 *
 */
@Component
public class SocketServer {

    @Autowired
    private ISocketService socketService;

    /**
     * 客户端连接的时候触发
     *
     * @param client
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        socketService.onConnect(client);
    }

    /**
     * 客户端关闭连接时触发
     *
     * @param client
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        socketService.onDisconnect(client);
    }

    /**
     * 客户端事件
     *
     * @param client  　客户端信息
     * @param request   请求信息
     * @param data    　客户端发送数据
     */
    @OnEvent(value = "message")
    public void onEvent(SocketIOClient client, AckRequest request, SocketMessage data) {
        socketService.onEvent(client, request, data);
    }
}
