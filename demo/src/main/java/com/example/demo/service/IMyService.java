package com.example.demo.service;

import com.example.demo.entity.DblogEntity;

public interface IMyService {

    public int insertServant(String name);

    public DblogEntity getMyFixLogOfOne(Long id);
}
