package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.dao.sourceCompany.entity.Servant;

public interface IServantService {

    int insertServant(String name);

    Page<Servant> getList(String name, String eName, String sex, Long star, Long pageIndex, Long pageSize);

}
