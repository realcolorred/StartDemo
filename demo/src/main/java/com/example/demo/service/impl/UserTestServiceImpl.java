package com.example.demo.service.impl;

import com.example.demo.dao.sourceHome.ServantMapper;
import com.example.demo.service.BaseService;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lenovo on 2019/9/11.
 */
@Service
public class UserTestServiceImpl extends BaseService implements IUserService {

    @Autowired
    private ServantMapper servantMapper;

    @Override
    public void insertOne() {
        servantMapper.insert("大法师");
        int i = 0;
        if (123 / i == 1) {
            return;
        }
    }
}
