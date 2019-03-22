package com.example.demo.service;

import com.example.demo.entity.DblogEntity;

import java.util.List;

public interface IMyService {

    public int insertServant(String name);

    public DblogEntity getMyFixLogOfOne(Long id);

    public List<DblogEntity> getMyFixLogDefault();
}
