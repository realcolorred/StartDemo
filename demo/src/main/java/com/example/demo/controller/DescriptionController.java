package com.example.demo.controller;

import com.example.common.description.MethodDescriptionBo;
import com.example.common.description.ParameterDescriptionBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by new on 2020/9/4.
 *
 * 接口描述
 */
@Api(description = "接口描述")
@RestController
@RequestMapping("/description")
@Slf4j
public class DescriptionController {

    private static String      ignoreMethod = "wait,equals,toString,hashCode,getClass,notify,notifyAll";
    private static Set<String> ignoreSet    = new HashSet<>(Arrays.asList(ignoreMethod.split(",")));

    @ApiOperation("获取indexController的描述")
    @GetMapping("/index")
    public ResponseEntity<String> getDescription() {
        StringBuilder result = new StringBuilder();
        Method[] methods = IndexController.class.getMethods();
        for (Method method : methods) {
            if (ignoreSet.contains(method.getName())) {
                continue;
            }

            MethodDescriptionBo methodDe = new MethodDescriptionBo();
            methodDe.setName(method.getName());
            ApiOperation apiOperation = method.getDeclaredAnnotation(ApiOperation.class);
            if (apiOperation != null) {
                methodDe.setDescription(apiOperation.value());
            }
            // TODO 未实现
            RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
            if (requestMapping != null) {
                methodDe.setUrl(requestMapping.value().toString());
            }
            Parameter[] parameters = method.getParameters();
            if (parameters != null) {
                for (Parameter parameter : parameters) {
                    ParameterDescriptionBo paramDe = new ParameterDescriptionBo();
                    paramDe.setName(parameter.getName());
                    paramDe.setType(parameter.getType().getTypeName());
                    ApiParam apiParam = parameter.getDeclaredAnnotation(ApiParam.class);
                    if (apiParam != null) {
                        paramDe.setDescription(apiParam.value());
                    }
                    methodDe.getParamDeList().add(paramDe);
                }
            }
            result.append(methodDe.toString()).append("\n");
        }
        return ResponseEntity.ok().header("Content-Type", "text/plain").body(result.toString());
    }
}
