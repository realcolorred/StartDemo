package com.example.pubserv.config;

import org.apache.catalina.filters.RemoteIpFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/*
* 过滤器
*
* */
@Configuration
public class FilterConfiguration {

    protected static final Logger logger = LoggerFactory.getLogger(FilterConfiguration.class);

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
                logger.info("this is MyFilter,url :" + request.getRequestURI());
                filterChain.doFilter(srequest, sresponse);
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
}
