package com.example.demo.controller;

import com.example.demo.entity.KingEntity;
import com.example.demo.service.IKingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by lenovo on 2019/9/25.
 */
@RestController
@RequestMapping("/king")
public class KingController {

    @Autowired
    private IKingService kingService;

    @RequestMapping("/")
    public List<KingEntity> query() {
        return kingService.queryKingList();
    }

    @RequestMapping("/fromat")
    public Integer fromat() {
        return kingService.formatDate();
    }
}
