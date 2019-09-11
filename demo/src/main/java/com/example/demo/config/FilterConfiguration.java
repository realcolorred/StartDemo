package com.example.demo.config;

import org.apache.catalina.filters.RemoteIpFilter;
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

    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new MyFilter());
        registration.addUrlPatterns("/*"); //可不设置，默认过滤路径即为：/*
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("MyFilter");
        registration.setOrder(1); //过滤器的注册顺序
        return registration;
    }

    public class MyFilter implements Filter {
        @Override
        public void destroy() {
            // TODO Auto-generated method stub
        }

        @Override
        public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain) throws IOException, ServletException {
            // TODO Auto-generated method stub
            HttpServletRequest request = (HttpServletRequest) srequest;
            System.out.println("this is MyFilter,url :" + request.getRequestURI());
            filterChain.doFilter(srequest, sresponse);
        }

        @Override
        public void init(FilterConfig arg0) throws ServletException {
            // TODO Auto-generated method stub
        }
    }
}
