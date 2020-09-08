package com.example.demo.service;

import com.example.demo.dao.sourceCompany.entity.DatabaseLog;

import java.util.List;

/**
 * Created by lenovo on 2019/9/11.
 */
public interface ILocalLogService {

    public DatabaseLog getMyFixLogOfOne(Long id);

    public List<DatabaseLog> getMyFixLogDefault();
}
