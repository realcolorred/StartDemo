package com.example.demo.controller;

import com.example.common.exception.DemoException;
import com.example.common.exception.ErrorMessage;
import com.example.common.request.ApiRespResult;
import com.example.common.util.DateUtil;
import com.example.demo.bo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author realcolor
 */
@Api(description = "默认响应")
@RestController
@RequestMapping
@Slf4j
public class IndexController {

    @ApiOperation("日期返回")
    @GetMapping(value = { "/", "" })
    public String index() {
        return "启动成功,现在的时间是" + DateUtil.dateToStringRead(new Date());
    }

    @ApiOperation("延迟日期返回")
    @GetMapping(value = "/delay")
    public String delay() throws InterruptedException {
        Thread.sleep(3000);
        return "启动成功,现在的时间是" + DateUtil.dateToStringRead(new Date());
    }

    @ApiOperation("get方法测试，用于获取资源")
    @GetMapping("/test/get")
    public ApiRespResult<List<User>> getTest(@RequestParam(value = "error", required = false) boolean error) {
        if (error) {
            throw new DemoException(ErrorMessage.REQUEST_PARAM_ERROR, "测试！抛出错误！");
        }
        List<User> result = new ArrayList<>();
        result.add(new User("123", "123"));
        result.add(new User("124", "124"));
        return ApiRespResult.success(result);
    }

    @ApiOperation("post方法测试，用于创建或变更资源，非幂等")
    @PostMapping("/test/post")
    public ApiRespResult<String> postTest(@RequestBody User user) {
        return ApiRespResult.success("post请求成功，入参为:" + user);
    }

    @ApiOperation("put方法测试，用于创建或变更资源，幂等")
    @PutMapping("/test/put")
    public ApiRespResult<String> putTest(@RequestBody User user) {
        return ApiRespResult.success("put请求成功，入参为:" + user);
    }

    @ApiOperation("delete方法测试，用于删除资源")
    @DeleteMapping("/test/delete/{id}")
    public ApiRespResult<String> deleteTest(@PathVariable("id") String id) {
        return ApiRespResult.success(id + "删除成功");
    }

    @ApiOperation("swagger注解测试")
    @PutMapping(value = "/test/{path1}/{path2}")
    @ApiResponses(value = { @ApiResponse(code = 0, message = "成功") })
    public ApiRespResult<String> fullMethod2(
        @ApiParam("参数1") @RequestParam(value = "param1", required = false, defaultValue = "swagger1") String param1,
        @ApiParam("参数2") @RequestParam(value = "param2", required = false, defaultValue = "swagger2") String param2,
        @ApiParam("必填参数1") @PathVariable(name = "path1") String path1, @ApiParam("必填参数2") @PathVariable(name = "path2") String path2,
        @ApiParam("body参数") @RequestBody(required = false) User user) {

        return ApiRespResult.success(param1 + "\n" + param2 + "\n" + path1 + "\n" + path2 + "\n" + user);
    }
}
