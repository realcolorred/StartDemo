package com.example.demo.service;

import com.example.demo.entity.DblogEntity;

import java.util.List;

/**
 * Created by lenovo on 2019/9/11.
 */
public interface ILocalLogService {

    public DblogEntity getMyFixLogOfOne(Long id);

    public List<DblogEntity> getMyFixLogDefault();
}
