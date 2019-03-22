package com.example.demo.controller;

import com.example.demo.entity.DblogEntity;
import com.example.demo.service.IMyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lenovo on 2019/3/22.
 */
@RestController
@RequestMapping("/myFixLog")
public class MyFixLogController {

    @Autowired
    @Qualifier("myService")
    private IMyService myService;

    @RequestMapping("/")
    public String hello() {
        return "这是我的修复日志查询分支";
    }

    @RequestMapping("/getOneById")
    public DblogEntity getOneById(Long id) {
        DblogEntity entity = myService.getMyFixLogOfOne(id);
        return entity;
    }
}
