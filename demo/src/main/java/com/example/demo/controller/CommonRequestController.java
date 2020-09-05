package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.common.exception.DemoException;
import com.example.common.exception.ErrorMessage;
import com.example.common.util.CollectionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by new on 2020/9/4.
 */
@Api(description = "通用调用方法")
@RestController
@RequestMapping("/request")
@Slf4j
public class CommonRequestController {

    @ApiOperation("通用调用方法")
    @PostMapping("/{controllerName}/{methodName}")
    public Object doIt(@ApiParam("controller名称") @PathVariable(name = "controllerName") String controllerName,
                       @ApiParam("方法名称") @PathVariable(name = "methodName") String methodName,
                       @ApiParam("参数") @RequestBody(required = false) List<Object> objectList)
        throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {

        // TODO 要限制能调用的方法！
        controllerName = "IndexController";
        Class cl = Class.forName("com.example.demo.controller." + controllerName);
        for (Method method : cl.getMethods()) {
            if (method.getName().equals(methodName) && isSameParam(method.getParameters(), objectList)) {
                if (CollectionUtil.isNotEmpty(objectList)) {
                    List<Object> newObjList = convertObject(method.getParameters(), objectList);
                    return method.invoke(cl.newInstance(), newObjList.toArray());
                } else {
                    return method.invoke(cl.newInstance());
                }
            }
        }
        throw new DemoException(ErrorMessage.REQUEST_NOFOUND_ERROR, controllerName, methodName);
    }

    private boolean isSameParam(Parameter[] parameters, List<Object> objectList) {
        if ((parameters == null || parameters.length == 0) && CollectionUtil.isEmpty(objectList)) {
            return true;
        }
        if (parameters != null && CollectionUtil.isNotEmpty(objectList) && parameters.length == objectList.size()) {
            // 这里只进行简单的参数个数判断
            return true;
        }
        return false;
    }

    private List<Object> convertObject(Parameter[] parameters, List<Object> objectList) {
        List<Object> newObjList = new ArrayList<>();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Class parameterClass = parameter.getType();
            Object object = objectList.get(i);
            Class objectClass = object.getClass();

            if (parameterClass != objectClass) {
                // TODO 还有未知情况没有处理，发现再说吧
                if ((objectClass == Integer.class || objectClass == int.class) && (parameterClass == Long.class || parameterClass == long.class)) {
                    newObjList.add(Long.valueOf((Integer) objectList.get(i)));
                } else if (objectClass == LinkedHashMap.class) {
                    newObjList.add(JSON.parseObject(JSON.toJSONString(object), parameterClass));
                } else {
                    newObjList.add(objectList.get(i));
                }
            } else {
                newObjList.add(objectList.get(i));
            }
        }
        return newObjList;
    }
}
