package com.example.demo.component;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by lenovo on 2019/9/12.
 */
@Component
public class MyPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rowPassword) {
        return rowPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rowPassword, String encodedPassword) {
        return encode(rowPassword).equals(encodedPassword);
    }
}
