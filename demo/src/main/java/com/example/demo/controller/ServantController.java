package com.example.demo.controller;

import com.example.common.request.ApiRespResult;
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
    public ApiRespResult<Integer> insertServantTest() {
        int ret = servantService.insertServant("我是一个测试");
        return ApiRespResult.success(ret);
    }

    @PostMapping(value = "/insert")
    public ApiRespResult<Integer> insertServant(@RequestParam String name) {
        int ret = servantService.insertServant(name);
        return ApiRespResult.success(ret);
    }
}
