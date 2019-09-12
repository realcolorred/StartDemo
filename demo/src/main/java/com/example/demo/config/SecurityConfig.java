package com.example.demo.config;

import com.alibaba.fastjson.JSON;
import com.example.demo.bean.MyResponseBean;
import com.example.demo.component.MyPasswordEncoder;
import com.example.demo.component.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by lenovo on 2019/9/11.
 * 安全设置
 */
@Configuration
@EnableWebSecurity // 开启web安全
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启全局方法级安全(启用表达式注解)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserService     myUserService;
    @Autowired
    private MyPasswordEncoder myPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserService).passwordEncoder(myPasswordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/index.html", "/static/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors().disable(); // 开启跨域访问
        httpSecurity.csrf().disable(); // 开启模拟请求，比如API POST测试工具的测试，不开启时，API POST为报403错误

        httpSecurity.authorizeRequests() // 请求授权限制
            .antMatchers("/admin/**").hasRole("ADMIN") //
            .anyRequest().permitAll(); //

        httpSecurity.formLogin() // 登录表单
            .loginPage("/login_p").loginProcessingUrl("/login") //
            .usernameParameter("username").passwordParameter("password") //
            .failureHandler((httpServletRequest, httpServletResponse, e) -> {
                httpServletResponse.setContentType("application/json;charset=utf-8");
                PrintWriter out = httpServletResponse.getWriter();
                out.write(JSON.toJSONString(new MyResponseBean(401, "登录失败")));
                out.flush();
                out.close();
            }) //
            .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                PrintWriter out = httpServletResponse.getWriter();
                out.write(JSON.toJSONString(new MyResponseBean(200, "登录成功")));
                out.flush();
                out.close();
            });

        httpSecurity.logout() // 登出消息
            .logoutSuccessHandler(((request, response, authentication) -> {
                PrintWriter out = response.getWriter();
                out.write(JSON.toJSONString(new MyResponseBean(200, "退出成功", authentication)));
                out.flush();
                out.close();
            }));

        httpSecurity.exceptionHandling() // 异常处理
            .accessDeniedHandler((request, response, ex) -> {
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                PrintWriter out = response.getWriter();
                out.write(JSON.toJSONString(new MyResponseBean(403, "权限不足")));
                out.flush();
                out.close();
            });

        httpSecurity.httpBasic() // 未登录时，进行提示
            .authenticationEntryPoint((request, response, authException) -> {
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                PrintWriter out = response.getWriter();
                out.write(JSON.toJSONString(new MyResponseBean(403, "未登录")));
                out.flush();
                out.close();
            });
    }

}
