package com.example.demo.controller;

import com.example.demo.service.IServantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/servant")
public class ServantController {

    @Autowired
    private IServantService servantService;

    @PostMapping(value = "/insertAsTest")
    public int insertServantTest() {
        return servantService.insertServant("我是一个测试");
    }

    @PostMapping(value = "/insert")
    public int insertServant(@RequestParam String name) {
        return servantService.insertServant(name);
    }
}
