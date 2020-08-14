//package com.example.demo.component;
//
//import com.example.pub.util.StringHelper;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by lenovo on 2019/9/12.
// */
//@Component
//public class MyUserService implements UserDetailsService {
//
//    @Override
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        String testUserName = "123";
//        String testPassword = "123";
//        String testauthoritie = "ROLE_ADMIN";
//
//        if (StringHelper.isEmpty(userName)) {
//            throw new UsernameNotFoundException("用户名不能为空");
//        } else if (!testUserName.equals(userName)) {
//            throw new UsernameNotFoundException("用户名或者密码不正确");
//        }
//
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(testauthoritie));
//        return new User(userName, testPassword, authorities);
//    }
//}
