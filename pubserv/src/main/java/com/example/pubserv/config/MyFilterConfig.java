package com.example.pubserv.config;

import com.example.common.util.DateUtil;
import com.example.common.util.StringUtil;
import com.example.common.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/*
* 过滤器
*
* */
@Configuration
@Slf4j
public class MyFilterConfig {

    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

    @Bean
    public FilterRegistrationBean<Filter> testFilterRegistration() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new Filter() {
            @Override
            public void destroy() {
                // TODO
            }

            @Override
            public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain) throws IOException, ServletException {
                // TODO
                HttpServletRequest request = (HttpServletRequest) srequest;
                Date timeStart = new Date();
                String uid = UUIDUtil.getUUID();
                log.info("请求uid:{} 开始,当前请求的url:{},当前请求的来源ip:{},当前请求的开始时间:{},请求参数:{}", uid, request.getRequestURI(), getClientIp(request),
                         DateUtil.dateToStringRead(timeStart), "TODO");
                filterChain.doFilter(srequest, sresponse);
                Date timeEnd = new Date();
                log.info("请求uid:{} 结束,当前请求的结束时间:{},当前请求耗时:{}ms,当前请求的返回结果:{}", uid, DateUtil.dateToStringRead(timeEnd),
                         timeEnd.getTime() - timeStart.getTime(), "TODO");
            }

            @Override
            public void init(FilterConfig arg0) throws ServletException {
                // TODO
            }
        });
        registration.addUrlPatterns("/*"); //可不设置，默认过滤路径即为：/*
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("MyFilter");
        registration.setOrder(1); //过滤器的注册顺序
        return registration;
    }

    /**
     * 获取客户端ip地址
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtil.isBlack(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtil.isBlack(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtil.isBlack(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 多个路由时，取第一个非unknown的ip
        final String[] arr = ip.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ip = str;
                break;
            }
        }
        return ip;
    }

    /**
     * 判断是否为ajax请求
     * @param request 1
     * @return 1
     */
    public static String getRequestType(HttpServletRequest request) {
        return request.getHeader("X-Requested-With");
    }
}
