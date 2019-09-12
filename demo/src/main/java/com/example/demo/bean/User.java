package com.example.demo.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private String userName;
    private String passWord;

    public User() {

    }

    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;

    }
}
