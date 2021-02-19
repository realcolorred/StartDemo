package com.example.demo.controller;

import com.example.demo.component.SocketServer;
import com.example.demo.service.ISocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by new on 2020/6/9.
 */
@RestController
@RequestMapping("/socket")
public class SocketController {

    @Autowired
    private ISocketService socketService;

    @GetMapping
    public ResponseEntity<String> query() {
        String msg = socketService.printStatus();
        return ResponseEntity.ok().header("Content-Type", "text/plain").body(msg);
    }
}
