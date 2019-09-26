package com.example.demo.controller;

import com.example.demo.util.DateUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created 冷萝卜 on 2019/9/10.
 */
@RestController
@RequestMapping("/")
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return getHello();
    }

    private String getHello() {
        return "启动成功,现在的时间是" + DateUtil.dateToString(new Date(), DateUtil.YYYYMMDDHHMMSS_read);
    }
}
