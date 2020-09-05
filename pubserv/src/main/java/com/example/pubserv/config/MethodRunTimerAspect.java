package com.example.pubserv.config;

import com.alibaba.fastjson.JSON;
import com.example.common.constant.LogConstants;
import com.example.common.exception.DemoException;
import com.example.common.request.ApiRespResult;
import com.example.common.util.DateUtil;
import com.example.common.util.StringUtil;
import com.example.common.util.UUIDUtil;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by new on 2020/8/26.
 *
 * 1 metrics指标收集
 * 2 抛错ApiRespResult化
 */
@Component
@Aspect
@Slf4j
public class MethodRunTimerAspect {

    static {
        // spring自动注入了，不用主动声明
        // Metrics.addRegistry(new SimpleMeterRegistry());
    }

    @Pointcut("execution(* com.example.*.controller.*.*(..))")
    public void PointcutDeclaration() {
    }

    /**
     * api切面
     * @param joinPoint 1
     * @return 1
     * @throws Throwable
     */
    @Around(value = "PointcutDeclaration()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Date startDate = new Date();
        String uuid = UUIDUtil.getUUID();

        // 请求开始日志
        writeRequestLog(requestURI, joinPoint.getArgs(), uuid, startDate);

        // timer示例，记录方法执行的执行的次数，最长耗时。参数名称为 method_cost_time_XXXXX，通过 /actuator/prometheus 访问。
        Timer timer = Metrics.timer("method.cost.time", "method.name", method.toString(), "method.url", requestURI);
        ThrowableHolder holder = new ThrowableHolder();
        Object result = timer.recordCallable(() -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable e) {
                holder.throwable = e;
            }
            return null;
        });
        //log.debug("接口{}的执行次数{}平均耗时{}ms最大耗时{}ms总耗时{}s", requestURI, timer.count(), timer.mean(TimeUnit.MILLISECONDS), timer.max(TimeUnit.MILLISECONDS), timer.totalTime(TimeUnit.SECONDS));

        // counter示例，统计所有接口调用总数
        Counter counter = Metrics.counter("method.count");
        counter.increment();
        //log.debug("接口调用总次数{}", counter.count());

        // gauge仪表示例，记录方法是否可用
        Tags tags = Tags.empty().and("method.ur", requestURI).and("method.name", method.toString());
        AtomicInteger num = Metrics.gauge("method.available", tags, new AtomicInteger(1));

        // 错误抛出改为 ApiRespResult 模式
        if (holder.throwable != null) {
            num.set(0);
            log.error("", holder.throwable);
            Throwable throwable = holder.throwable;

            // 反射目标异常特殊处理，取出异常
            if (throwable instanceof InvocationTargetException) {
                throwable = ((InvocationTargetException) throwable).getTargetException();
            }
            if (throwable instanceof DemoException) {
                DemoException demoException = (DemoException) throwable;
                result = ApiRespResult.fail(demoException.getCode(), MDC.get(LogConstants.REQUEST_ID), demoException.getMessage());
            } else {
                result = ApiRespResult.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), MDC.get(LogConstants.REQUEST_ID), throwable.toString());
            }
        } else {
            if (result instanceof ApiRespResult) {
                ((ApiRespResult) result).setRequestId(MDC.get(LogConstants.REQUEST_ID));
            }
        }

        // 请求结束日志
        writeResponseLog(result, uuid, startDate);

        return result;
    }

    private void writeRequestLog(String url, Object[] requests, String uuid, Date startDate) {
        String requestParameter = "";
        for (Object request : requests) {
            if (request == null || request instanceof MultipartFile || request instanceof HttpServletRequest ||
                request instanceof HttpServletResponse) {
                continue;
            }

            String requestStr = request.toString();
            try {
                requestStr = JSON.toJSONString(request);
            } catch (Exception e) {
                // 能转就转，失败就算了
            }
            if (StringUtil.isNotEmpty(requestParameter)) {
                requestParameter += " " + requestStr;
            } else {
                requestParameter += requestStr;
            }
        }
        String startTime = DateUtil.dateToString(startDate, DateUtil.YYYYMMDDHHMMSSSSS_READ);
        log.info("REQ: {} START, START_TIME:{}, URL:{}, REQUEST_PARAMETER:{}", uuid, startTime, url, requestParameter);
    }

    private void writeResponseLog(Object response, String uuid, Date startDate) {
        Date endDate = new Date();
        String startTime = DateUtil.dateToString(startDate, DateUtil.YYYYMMDDHHMMSSSSS_READ);
        String endTime = DateUtil.dateToString(endDate, DateUtil.YYYYMMDDHHMMSSSSS_READ);
        long costTime = endDate.getTime() - startDate.getTime();
        String result = response.toString();
        try {
            result = JSON.toJSONString(response);
        } catch (Exception e) {
            // 能转就转，失败就算了
        }
        log.info("REQ: {} END, DATE FROM:{} TO {} COST:{}ms, RESPONSE:{}", uuid, startTime, endTime, costTime, result);
    }

    private class ThrowableHolder {
        Throwable throwable;
    }
}
