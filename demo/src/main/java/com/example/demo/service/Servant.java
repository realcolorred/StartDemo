package com.example.demo.service;

import com.example.demo.dao.ServantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("servant")
public class Servant {
    @Autowired
    @Qualifier("servantMapper")
    private ServantMapper servantMapper;

    public int insertServant() {
        return servantMapper.insert("梅林");
    }
}
