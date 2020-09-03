package com.example.pubserv.config;

import com.example.common.constant.LogConstants;
import com.example.common.util.StringUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created by new on 2020/9/3.
 */
@Configuration
@Slf4j
public class FeignConfiguration implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String values = request.getHeader(name);
                template.header(name, values);
            }
        }

        String reqId = request.getHeader(LogConstants.REQUEST_ID);
        String reqMDC = MDC.get(LogConstants.REQUEST_ID);
        if (!template.headers().containsKey(LogConstants.REQUEST_ID)) {
            if (StringUtil.isNotEmpty(reqMDC)) {
                template.header(LogConstants.REQUEST_ID, reqMDC);
            } else if (StringUtil.isNotEmpty(reqId)) {
                template.header(LogConstants.REQUEST_ID, reqId);
            }
        }
        //log.debug("feign请求的请求头复制结束");
    }
}
