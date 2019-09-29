package com.example.demo.service.impl;

import com.example.demo.dao.sourceCompany.ServantMapper;
import com.example.demo.service.IServantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServantServiceImpl extends BaseService implements IServantService {

    @Autowired
    private ServantMapper servantMapper;

    @Override
    public int insertServant(String name) {
        int ret = 0;
        if (isNotEmpty(name)) {
            ret += servantMapper.insert(name);
        }
        return ret;
    }
}
