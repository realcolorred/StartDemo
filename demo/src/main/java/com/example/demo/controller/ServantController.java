package com.example.demo.controller;

import com.example.demo.service.IMyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/servant")
public class ServantController {

    @Autowired
    @Qualifier("myService")
    private IMyService servantService;

    @RequestMapping("/insertAsTest")
    public int insertServantTest() {
        return servantService.insertServant("我是一个测试");
    }

    @RequestMapping("/insert")
    public int insertServant(@RequestParam String name) {
        return servantService.insertServant(name);
    }
}
