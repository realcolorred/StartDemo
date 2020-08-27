package com.example.pubserv.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by new on 2020/8/26.
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
     * 统计接口方法执行的次数，最长耗时。通过 /actuator/prometheus 访问。参数名称为 method_cost_time_XXXXX
     * @param joinPoint 1
     * @return
     * @throws Throwable
     */
    @Around(value = "PointcutDeclaration()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI();
        //Timer timer = Metrics.timer("method.cost.time", "method.name", method.toString(), "method.url", requestURI);
        Timer timer = Metrics.timer("method.cost.time", "method.url", requestURI);
        ThrowableHolder holder = new ThrowableHolder();
        Object result = timer.recordCallable(() -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable e) {
                holder.throwable = e;
            }
            return null;
        });
        log.debug("接口{}的执行次数{}平均耗时{}ms最大耗时{}ms总耗时{}s", requestURI, timer.count(), timer.mean(TimeUnit.MILLISECONDS), timer.max(TimeUnit.MILLISECONDS),
                  timer.totalTime(TimeUnit.SECONDS));

        Counter counter = Metrics.counter("method.count");
        counter.increment();
        log.debug("接口调用总次数{}", counter.count());

        Tags tags = Tags.empty().and("method.ur", requestURI);
        AtomicInteger num = Metrics.gauge("method.available", tags, new AtomicInteger(0));
        if (holder.throwable != null) {
            throw holder.throwable;
        } else {
            num.set(1);
        }
        return result;
    }

    private class ThrowableHolder {
        Throwable throwable;
    }
}
