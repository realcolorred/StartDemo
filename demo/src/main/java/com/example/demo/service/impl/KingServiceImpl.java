package com.example.demo.service.impl;

import com.example.demo.dao.sourceCompany.KingMgrMapper;
import com.example.demo.entity.KingEntity;
import com.example.demo.service.IKingService;
import com.example.demo.util.CollectionHelper;
import com.example.demo.util.DateHelper;
import com.example.demo.util.NumberHelper;
import com.example.demo.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lenovo on 2019/9/25.
 */
@Service
public class KingServiceImpl implements IKingService {

    @Autowired
    private KingMgrMapper kingMgrMapper;

    @Override
    public List<KingEntity> queryKingList() {
        return kingMgrMapper.getList();
    }

    @Override
    public int updateKing(KingEntity entity) {
        if (!NumberHelper.isVaildNum(entity.getId()))
            return 0;

        return kingMgrMapper.update(entity);
    }

    @Override
    public int formatDate() {
        List<KingEntity> list = kingMgrMapper.getList();
        if (CollectionHelper.isNotEmpty(list)) {
            for (KingEntity entity : list) {
                KingEntity newEntity = new KingEntity();
                newEntity.setId(entity.getId());
                if (StringHelper.isNotEmpty(entity.getRuleStart())) {
                    newEntity.setRuleStartInt(DateHelper.getDayOfTime(entity.getRuleStart()));
                }
                if (StringHelper.isNotEmpty(entity.getRuleStart()) && StringHelper.isNotEmpty(entity.getRuleEnd())) {
                    newEntity.setRuleTime(DateHelper.getYearBetween(entity.getRuleStart(), entity.getRuleEnd()));
                }
                kingMgrMapper.update(newEntity);
            }
            return 1;
        }
        return 0;
    }
}
