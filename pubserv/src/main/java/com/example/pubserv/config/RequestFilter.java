package com.example.pubserv.config;

import com.example.common.constant.LogConstants;
import com.example.common.util.DateUtil;
import com.example.common.util.StringUtil;
import com.example.common.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * 过滤器
 *
 * 没啥用
 *
 * contentType：
 *  常见的媒体格式类型如下：
 *      text/html ： HTML格式
 *      text/plain ：纯文本格式
 *      text/xml ： XML格式
 *      image/gif ：gif图片格式
 *      image/jpeg ：jpg图片格式
 *      image/png：png图片格式
 *  以application开头的媒体格式类型：
 *      application/xhtml+xml ：XHTML格式
 *      application/xml： XML数据格式
 *      application/atom+xml ：Atom XML聚合格式
 *      application/json： JSON数据格式
 *      application/pdf：pdf格式
 *      application/msword ： Word文档格式
 *      application/octet-stream ： 二进制流数据（如常见的文件下载）
 *      application/x-www-form-urlencoded ： <form encType=””>中默认的encType，form表单数据被编码为key/value格式发送到服务器（表单默认的提交数据的格式）
 *  另外一种常见的媒体格式是上传文件之时使用的：
 *      multipart/form-data ： 需要在表单中进行文件上传时，就需要使用该格式
 */
@Configuration
@Slf4j
public class RequestFilter {

    @Bean
    public FilterRegistrationBean<Filter> testFilterRegistration() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new Filter() {
            @Override
            public void init(FilterConfig arg0) throws ServletException {
                // TODO
            }

            @Override
            public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain) throws IOException, ServletException {
                HttpServletRequest request = (HttpServletRequest) srequest;
                HttpServletResponse response = (HttpServletResponse) sresponse;

                // 将请求id，用户id写入日志头
                putRequestId(request);

                //String uid = UUIDUtil.getUUID();
                //Date timeStart = new Date();
                //log.info("请求uid:{} 开始,当前请求的url:{},当前请求的来源ip:{},当前请求的开始时间:{}", uid, request.getRequestURI(), getClientIp(request), DateUtil.dateToString(timeStart, DateUtil.YYYYMMDDHHMMSSSSS_READ));

                // 执行
                filterChain.doFilter(srequest, sresponse);

                //Date timeEnd = new Date();
                //log.info("请求uid:{} 结束,当前请求的结束时间:{},当前请求耗时:{}ms,当前请求的状态码:status={}", uid, DateUtil.dateToString(timeEnd, DateUtil.YYYYMMDDHHMMSSSSS_READ), timeEnd.getTime() - timeStart.getTime(),response.getStatus());

                // 清除请求id
                clearRequestId();
            }

            @Override
            public void destroy() {
                // TODO
            }
        });
        registration.addUrlPatterns("/*"); //可不设置，默认过滤路径即为：/*
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("MyFilter");
        registration.setOrder(1); //过滤器的注册顺序
        return registration;
    }

    public static void putRequestId(HttpServletRequest request) {
        if (StringUtil.isEmpty(MDC.get(LogConstants.REQUEST_ID))) {
            String requestId = request.getHeader(LogConstants.REQUEST_ID);
            if (StringUtil.isEmpty(requestId)) {
                MDC.put(LogConstants.REQUEST_ID, UUIDUtil.getUUID());
            } else {
                MDC.put(LogConstants.REQUEST_ID, requestId);
            }
        }
    }

    public static void clearRequestId() {
        MDC.remove(LogConstants.REQUEST_ID);
    }

    /**
     * 获取客户端ip地址
     * @param request 1
     * @return 1
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
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
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"));
    }
}
