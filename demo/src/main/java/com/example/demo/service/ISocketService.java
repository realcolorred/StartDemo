package com.example.demo.service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.example.demo.bo.SocketMessage;

/**
 * Created by new on 2021/2/3.
 */
public interface ISocketService {

    public void onConnect(SocketIOClient client);

    public void onDisconnect(SocketIOClient client);

    public void onEvent(SocketIOClient client, AckRequest request, SocketMessage data);

    public String printStatus();
}
