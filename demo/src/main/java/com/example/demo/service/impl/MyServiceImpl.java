package com.example.demo.service.impl;

import com.example.demo.dao.MyFixLogMapper;
import com.example.demo.dao.ServantMapper;
import com.example.demo.entity.DblogEntity;
import com.example.demo.service.IMyService;
import com.example.demo.util.NumberHelper;
import com.example.demo.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("myService")
public class MyServiceImpl implements IMyService {

    @Autowired
    @Qualifier("servantMapper")
    private ServantMapper servantMapper;

    @Autowired
    @Qualifier("myFixLogMapper")
    private MyFixLogMapper myFixLogMapper;

    public int insertServant(String name) {
        int ret = 0;
        if (StringHelper.isNotEmpty(name)) {
            ret += servantMapper.insert(name);
        }
        return ret;
    }

    @Override
    public DblogEntity getMyFixLogOfOne(Long id) {
        if (NumberHelper.isVaildLong(id)) {
            return myFixLogMapper.getOne(id);
        }
        return null;
    }
}
