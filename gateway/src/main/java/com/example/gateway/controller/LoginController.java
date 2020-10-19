package com.example.gateway.controller;

import com.alibaba.fastjson.JSON;
import com.example.common.request.ApiRespResult;
import com.example.demoapi.api.UserApi;
import com.example.demoapi.dto.UserInfoDto;
import com.example.gateway.util.JWTSigner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by new on 2020/8/15.
 */
@Api(description = "登录")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserApi userApi;

    @ApiOperation("登录")
    @PostMapping
    public String login(@ApiParam("用户名称") @RequestParam(value = "userName") String userName,
                        @ApiParam("用户密码") @RequestParam(value = "password") String password) {
        ApiRespResult<UserInfoDto> respResult = userApi.login(userName, password);
        if (respResult.isSuccess()) {
            return JWTSigner.sign(JSON.toJSONString(respResult.getData()));
        } else {
            return respResult.getMessage();
        }
    }
}
