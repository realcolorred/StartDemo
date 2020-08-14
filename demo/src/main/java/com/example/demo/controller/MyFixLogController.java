package com.example.demo.controller;

import com.example.demo.entity.DblogEntity;
import com.example.demo.service.ILocalLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by lenovo on 2019/3/22.
 */
@RestController
@RequestMapping("/log")
public class MyFixLogController {

    @Autowired
    private ILocalLogService localLogService;

    @GetMapping("/")
    public List<DblogEntity> hello() {
        return localLogService.getMyFixLogDefault();
    }

    @GetMapping("/getOneById")
    public DblogEntity getOneById(Long id) {
        DblogEntity entity = localLogService.getMyFixLogOfOne(id);
        return entity;
    }

}
