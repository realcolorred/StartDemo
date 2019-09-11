package com.example.demo.service.impl;

import com.example.demo.service.IUserService;
import com.example.demo.util.StringHelper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2019/9/11.
 */
@Service
public class UserTestServiceImpl implements IUserService {

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        String testUserName = "123";
        String testPassword = "123";
        String testRoleName = "/admin/**";

        if (StringHelper.isEmpty(userName)) {
            throw new UsernameNotFoundException("用户名不能为空");
        } else if (!testUserName.equals(userName)) {
            throw new UsernameNotFoundException("用户名或者密码不正确");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(testRoleName));
        return new User(userName, testPassword, authorities);
    }
}
