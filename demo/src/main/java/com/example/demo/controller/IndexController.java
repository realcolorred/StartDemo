package com.example.demo.controller;

import com.example.demo.bo.User;
import com.example.pub.util.DateHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author realcolor
 */
@Api(description = "默认响应")
@RestController
@RequestMapping
public class IndexController {

    @ApiOperation("日期返回")
    @GetMapping(value = { "/", "" })
    public String index() {
        return getHello();
    }

    @ApiOperation("延迟日期返回")
    @GetMapping(value = "/delay")
    public String delay() throws InterruptedException {
        Thread.sleep(3000);
        return getHello();
    }

    private String getHello() {
        return "启动成功,现在的时间是" + DateHelper.dateToString(new Date(), DateHelper.YYYYMMDDHHMMSS_READ);
    }

    @ApiOperation("swagger注解测试")
    @PutMapping(value = "/test/{path1}/{path2}")
    @ApiResponses(value = { @ApiResponse(code = 0, message = "成功") })
    public String fullMethod2(@ApiParam("参数1") @RequestParam(value = "param1", required = false, defaultValue = "swagger1") String param1,
                              @ApiParam("参数2") @RequestParam(value = "param2", required = false, defaultValue = "swagger2") String param2,
                              @ApiParam("必填参数1") @PathVariable(name = "path1") String path1,
                              @ApiParam("必填参数2") @PathVariable(name = "path2") String path2,
                              @ApiParam("body参数") @RequestBody(required = false) User user) {
        return param1 + "\n" + param2 + "\n" + path1 + "\n" + path2 + "\n" + user;
    }
}
