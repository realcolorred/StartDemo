package com.example.gateway.controller;

import com.example.gateway.util.JWTSigner;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by new on 2020/8/15.
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping
    public String login(String userName, String password) {
        return JWTSigner.sign(userName + "+" + password);
    }
}
