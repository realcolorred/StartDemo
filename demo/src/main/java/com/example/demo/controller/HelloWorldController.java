package com.example.demo.controller;

import com.example.demo.util.DateHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class HelloWorldController {
    @RequestMapping("/hello")
    public String index() {
        return "项目启动成功,现在的时间是" + DateHelper.toString(new Date(), DateHelper.YYYYMMDDHHMMSS_read);
    }
}
