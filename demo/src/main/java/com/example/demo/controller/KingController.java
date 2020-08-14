package com.example.demo.controller;

import com.example.demo.entity.KingEntity;
import com.example.demo.service.IKingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @GetMapping({ "/query", "/", "" })
    public List<KingEntity> query() {
        return kingService.queryKingList();
    }

    @PutMapping("/formatData")
    public Integer formatData() {
        return kingService.formatData();
    }

    @PostMapping(value = "/insertKing")
    public Integer insertKing(@Validated KingEntity entity) {
        return kingService.insertKing(entity);
    }
}
