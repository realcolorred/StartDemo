package com.example.demo.controller;

import com.example.demo.util.DateHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created on 2019/9/10.
 */
@RestController
@RequestMapping("/")
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return getHello();
    }

    private String getHello() {
        return "项目启动成功,现在的时间是" + DateHelper.toString(new Date(), DateHelper.YYYYMMDDHHMMSS_read);
    }
}
