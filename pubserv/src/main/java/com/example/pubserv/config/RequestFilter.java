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
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 过滤器
 *
 * 记录请求以及返回结果日志
 */
@Configuration
@Slf4j
public class RequestFilter {

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
                String uid = UUIDUtil.getUUID();

                Date timeStart = new Date();
                HttpServletRequest request = (HttpServletRequest) srequest;
                log.info("请求uid:{} 开始,当前请求的url:{},当前请求的来源ip:{},当前请求的开始时间:{},url参数:{}", //
                         uid, request.getRequestURI(), getClientIp(request), DateUtil.dateToString(timeStart, DateUtil.YYYYMMDDHHMMSSSSS_READ),
                         getParamString(request));

                // 执行
                filterChain.doFilter(srequest, sresponse);

                Date timeEnd = new Date();
                HttpServletResponse response = (HttpServletResponse) sresponse;
                ServletOutputStream outputStream = response.getOutputStream();
                log.info("请求uid:{} 结束,当前请求的结束时间:{},当前请求耗时:{}ms,当前请求的返回结果:status={},body={}", //
                         uid, DateUtil.dateToString(timeEnd, DateUtil.YYYYMMDDHHMMSSSSS_READ), timeEnd.getTime() - timeStart.getTime(),
                         response.getStatus(), outputStream);
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

    protected static String getParamString(HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        StringBuilder sb = new StringBuilder();
        String reqBody = null;
        try {
            for (Map.Entry<String, String[]> e : map.entrySet()) {
                sb.append(e.getKey()).append("=");
                String[] value = e.getValue();
                if (value != null && value.length == 1) {
                    sb.append(value[0]).append("&");
                } else {
                    sb.append(Arrays.toString(value)).append("\n");
                }
            }
            reqBody = URLDecoder.decode(sb.toString(), "UTF-8");
            if (reqBody.contains("{") && reqBody.contains("}")) {
                reqBody = reqBody.substring(reqBody.indexOf("{"));
                reqBody = reqBody.substring(0, reqBody.lastIndexOf("}") + 1);
            } else if (reqBody.contains("&")) {
                reqBody = reqBody.substring(0, reqBody.lastIndexOf("&"));
            }
        } catch (UnsupportedEncodingException e1) {
            log.error("", e1);
        }
        return reqBody;
    }

    /**
     * 获取文件上传表单参数
     *
     * @param request
     * @return
     * @throws IOException
     */
    protected void getFormDataParam(HttpServletRequest request, Map<Object, Object> paramMap) throws IOException {
        String contentType = request.getContentType();
        if (contentType == null || (!contentType.contains("multipart/form-data") && !contentType.contains("text/plain"))) {//只接受文件上传表单和raw内容
            return;
        }

        String paramStr = null;
        try {
            paramStr = IOUtils.read(request.getReader());
        } catch (IOException e) {
            log.error("获取表单参数失败", e);
        }
        int index = 0;
        if (paramStr != null && paramStr.length() > 0) {
            if (contentType.contains("multipart/form-data")) {//form-data类型表单
                String spiltStr = paramStr.substring(0, paramStr.indexOf("Content-Disposition:"));
                String[] split = paramStr.split(spiltStr);

                for (String s : split) {
                    if (StringUtil.isBlank(s) || s.contains("filename=") || !s.contains("Content-Disposition: form-data;"))
                        continue;
                    String singleParamStr = s.substring("Content-Disposition: form-data; ".length());
                    paramMap.put("parseParam_" + index, new String[] { singleParamStr });
                    index++;
                }
            } else {
                //raw类型表单
                paramMap.put("parseParam_" + index, new String[] { paramStr });
            }
        }
    }

    /**
     * 获取客户端ip地址
     * @param request
     * @return
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
