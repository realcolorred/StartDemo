package com.example.demo.service.impl;

import com.example.demo.dao.sourceHome.ServantMapper;
import com.example.demo.service.IServantService;
import com.example.demo.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServantServiceImpl implements IServantService {

    @Autowired
    private ServantMapper servantMapper;

    @Override
    public int insertServant(String name) {
        int ret = 0;
        if (StringUtil.isNotEmpty(name)) {
            ret += servantMapper.insert(name);
        }
        return ret;
    }
}
