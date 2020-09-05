package com.example.demo.service.impl;

import com.example.demo.dao.sourceCompany.MyFixLogMapper;
import com.example.demo.entity.DatabaseLog;
import com.example.demo.service.ILocalLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lenovo on 2019/9/11.
 */
@Service
public class LocalLogServiceImpl extends BaseService implements ILocalLogService {

    @Autowired
    private MyFixLogMapper myFixLogMapper;

    @Override
    public DatabaseLog getMyFixLogOfOne(Long id) {
        if (isVaildNum(id)) {
            return myFixLogMapper.getOne(id);
        }
        return null;
    }

    @Override
    public List<DatabaseLog> getMyFixLogDefault() {
        return myFixLogMapper.getDefault();
    }
}
