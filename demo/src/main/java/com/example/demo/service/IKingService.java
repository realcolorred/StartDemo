package com.example.demo.service;

import com.example.demo.dao.sourceCompany.entity.King;

import java.util.List;

/**
 * Created by lenovo on 2019/9/25.
 */
public interface IKingService {

    List<King> queryKingList();

    int updateKing(King entity);

    int insertKing(King entity);

    int formatData();
}
