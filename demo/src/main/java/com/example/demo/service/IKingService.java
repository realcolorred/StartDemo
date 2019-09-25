package com.example.demo.service;

import com.example.demo.entity.KingEntity;

import java.util.List;

/**
 * Created by lenovo on 2019/9/25.
 */
public interface IKingService {

    List<KingEntity> queryKingList();

    int updateKing(KingEntity entity);

    int formatDate();
}
