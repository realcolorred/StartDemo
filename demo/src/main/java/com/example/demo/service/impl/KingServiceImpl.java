package com.example.demo.service.impl;

import com.example.demo.dao.sourceCompany.KingMgrMapper;
import com.example.demo.entity.KingEntity;
import com.example.demo.service.IKingService;
import com.example.demo.util.DateHelper;
import com.example.demo.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lenovo on 2019/9/25.
 */
@Service
public class KingServiceImpl extends BaseService implements IKingService {

    @Autowired
    private KingMgrMapper kingMgrMapper;

    @Override
    public List<KingEntity> queryKingList() {
        return kingMgrMapper.getList();
    }

    @Override
    public int updateKing(KingEntity entity) {
        if (!isVaildNum(entity.getId())){
            return 0;
        }
        return kingMgrMapper.update(entity);
    }

    @Override
    public int insertKing(KingEntity entity) {
        String msg = ValidatorUtil.validate(entity);
        if (isNotEmpty(msg)) {
            logger.warn("新增数据:" + entity + ",不合法:" + msg);
            return -1;
        }
        if (isEmpty(entity.getKingName())) {
            logger.warn("新增数据不合法:" + entity);
            return -1;
        }

        if (!isVaildNum(entity.getRuleStartInt()) && isNotEmpty(entity.getRuleStart())) {
            entity.setRuleStartInt(DateHelper.getTimes(entity.getRuleStart()));
        }
        fillEmptyBean(entity);
        return kingMgrMapper.insert(entity);
    }

    @Override
    public int formatData() {
        List<KingEntity> list = kingMgrMapper.getList();
        if (isNotEmpty(list)) {
            for (KingEntity entity : list) {
                KingEntity newEntity = new KingEntity();
                newEntity.setId(entity.getId());
                if (isNotEmpty(entity.getRuleStart())) {
                    newEntity.setRuleStartInt(DateHelper.getTimes(entity.getRuleStart()));
                }
                if (isNotEmpty(entity.getRuleStart()) && isNotEmpty(entity.getRuleEnd())) {
                    newEntity.setRuleTime(DateHelper.getYearBetween(entity.getRuleStart(), entity.getRuleEnd()));
                }
                if (isNotEmpty(entity.getBirthday()) && isNotEmpty(entity.getDeathday())) {
                    newEntity.setAge(DateHelper.getYearBetween(entity.getBirthday(), entity.getDeathday()));
                }
                kingMgrMapper.update(newEntity);
            }
            return list.size();
        }
        return 0;
    }
}
