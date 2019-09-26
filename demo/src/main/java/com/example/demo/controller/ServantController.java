package com.example.demo.controller;

import com.example.demo.service.IServantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/servant")
public class ServantController {

    @Autowired
    private IServantService servantService;

    @RequestMapping(value = "/insertAsTest", method = RequestMethod.GET)
    public int insertServantTest() {
        return servantService.insertServant("我是一个测试");
    }

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public int insertServant(@RequestParam String name) {
        return servantService.insertServant(name);
    }
}
