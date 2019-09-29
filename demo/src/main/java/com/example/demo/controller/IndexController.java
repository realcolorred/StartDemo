package com.example.demo.controller;

import com.example.demo.service.IServantService;
import com.example.demo.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author realcolor
 */
@RestController
@RequestMapping("/")
public class IndexController {

    @Autowired
    private IServantService servantService;

    @RequestMapping("/")
    public String index() {
        return getHello();
    }

    private String getHello() {
        return "启动成功,现在的时间是" + DateUtil.dateToString(new Date(), DateUtil.YYYYMMDDHHMMSS_read);
    }
}
