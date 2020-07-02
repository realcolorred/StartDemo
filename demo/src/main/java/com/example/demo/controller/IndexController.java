package com.example.demo.controller;

import com.example.pub.util.DateHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author realcolor
 */
@RestController
@RequestMapping("/")
public class IndexController {

    @RequestMapping({ "/", "" })
    public String index() {
        return getHello();
    }

    private String getHello() {
        return "启动成功,现在的时间是" + DateHelper.dateToString(new Date(), DateHelper.YYYYMMDDHHMMSS_READ);
    }
}
