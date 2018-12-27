package com.example.demo.controller;

import com.example.demo.bean.User;
import com.example.demo.dao.ServantMapper;
import com.example.demo.service.Servant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @Autowired
    private Servant servant;

    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }

    @RequestMapping("/getUser")
    public User getUser() {
        User user = new User();
        user.setUserName("小明");
        user.setPassWord("xxxx");
        return user;
    }

    @RequestMapping("/insert")
    public int insert() {
        return servant.insertServant();
    }
}
