package com.example.demo.service.impl;

import com.example.demo.dao.sourceCompany.MyFixLogMapper;
import com.example.demo.dao.sourceHome.ServantMapper;
import com.example.demo.entity.DblogEntity;
import com.example.demo.service.IServantService;
import com.example.demo.util.NumberHelper;
import com.example.demo.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServantServiceImpl implements IServantService {

    @Autowired
    private ServantMapper servantMapper;

    public int insertServant(String name) {
        int ret = 0;
        if (StringHelper.isNotEmpty(name)) {
            ret += servantMapper.insert(name);
        }
        return ret;
    }
}
