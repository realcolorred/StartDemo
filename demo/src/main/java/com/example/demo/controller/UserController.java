package com.example.demo.controller;

import com.example.common.exception.DemoException;
import com.example.common.exception.ErrorMessage;
import com.example.common.request.ApiRespResult;
import com.example.common.util.StringUtil;
import com.example.demoapi.dto.UserInfoDto;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by new on 2020/9/15.
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/login")
    @ApiOperation("用户登录获取基本信息")
    public ApiRespResult<UserInfoDto> login(@RequestParam("userAccount") String userAccount, @RequestParam("userPassword") String userPassword) {
        if (StringUtil.isBlank(userAccount) || StringUtil.isBlank(userPassword)) {
            return ApiRespResult.fail(ErrorMessage.USER_LOGIN_BLACK);
        }
        if ("admin".equals(userAccount)) {
            return ApiRespResult.fail(ErrorMessage.USER_LOGIN_FORBID);
        }
        if ("user".equals(userAccount) && "123456".equals(userPassword)) {
            UserInfoDto userInfoDto = new UserInfoDto();
            userInfoDto.setUserId(1L);
            userInfoDto.setUserAccount(userAccount);
            userInfoDto.setUserName("张三");
            return ApiRespResult.success(userInfoDto);
        } else {
            return ApiRespResult.fail(ErrorMessage.USER_LOGIN_WRONG);
        }
    }

    @PostMapping("getPermission")
    @ApiOperation("获取用户拥有的权限")
    public ApiRespResult<List<String>> getPermission(@RequestParam("userId") Long userId) {
        List<String> strings = new ArrayList<>(Arrays.asList("/demo/test/*,/demo/servant/*".split(",")));
        return ApiRespResult.success(strings);
    }
}
