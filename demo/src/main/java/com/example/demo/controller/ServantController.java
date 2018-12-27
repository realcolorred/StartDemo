package com.example.demo.controller;

import com.example.demo.service.IServantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServantController {

    @Autowired
    @Qualifier("servant")
    private IServantService servantService;

    @RequestMapping("/insert")
    public int insertServant(@RequestParam String name) {
        return servantService.insertServant(name);
    }
}
