package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.exception.DemoException;
import com.example.common.exception.ErrorMessage;
import com.example.demo.dao.sourceCompany.ServantMapper;
import com.example.demo.entity.Servant;
import com.example.demo.service.IServantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServantServiceImpl extends BaseService implements IServantService {

    @Autowired
    private ServantMapper servantMapper;

    @Override
    public int insertServant(String name) {
        if (isEmpty(name)) {
            throw new DemoException(ErrorMessage.DATA_INSERT_FAIL, "名称不能为空");
        }

        QueryWrapper<Servant> wrapper = new QueryWrapper<>();
        wrapper.eq("servant_name_china", name);
        wrapper.select("servant_id");
        if (isNotEmpty(servantMapper.selectList(wrapper))) {
            throw new DemoException(ErrorMessage.DATA_INSERT_FAIL_EXIST, name);
        }

        Servant entity = new Servant();
        entity.setServantNameChina(name);
        return servantMapper.insert(entity);
    }

    @Override
    public Page<Servant> getList(String name, String eName, String sex, Long star, Long pageIndex, Long pageSize) {
        QueryWrapper<Servant> wrapper = new QueryWrapper<>();
        if (isNotEmpty(name)) {
            wrapper.like("servant_name_china", name);
        }
        if (isNotEmpty(eName)) {
            wrapper.like("servant_name_english", eName);
        }
        if (isNotEmpty(sex)) {
            wrapper.eq("sex", sex);
        }
        if (isVaildNum(star)) {
            wrapper.eq("star", star);
        }
        wrapper.orderByDesc("servant_id");
        Page<Servant> servantPage = new Page<>(pageIndex, pageSize);
        servantPage = servantMapper.selectPage(servantPage, wrapper);
        return servantPage;
    }
}
