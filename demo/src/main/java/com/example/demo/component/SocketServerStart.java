package com.example.demo.component;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by new on 2021/1/26.
 */
@Slf4j
@Component
public class SocketServerStart implements CommandLineRunner {

    @Autowired
    private SocketIOServer socketIOServer;

    @Override
    public void run(String... args) {
        socketIOServer.start();
        log.info("socket.io启动成功！");
    }
}
