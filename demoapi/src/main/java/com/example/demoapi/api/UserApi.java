package com.example.demoapi.api;

import com.example.common.request.ApiRespResult;
import com.example.demoapi.dto.UserInfoDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by new on 2020/9/15.
 */
@FeignClient(name = "demo", contextId = "user", path = "/user", url = "${demo-url:}")
public interface UserApi {

    @PostMapping("/login")
    public ApiRespResult<UserInfoDto> login(@RequestParam("userAccount") String userAccount, @RequestParam("userPassword") String userPassword);

    @PostMapping("getPermission")
    public ApiRespResult<List<String>> getPermission(@RequestParam("userId") Long userId);
}
