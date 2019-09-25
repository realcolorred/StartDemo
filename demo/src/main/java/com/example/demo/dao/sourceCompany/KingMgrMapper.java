package com.example.demo.dao.sourceCompany;

import com.example.demo.entity.KingEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by lenovo on 2019/9/25.
 */
public interface KingMgrMapper {

    @Select("select * from king where id = #{id}")
    KingEntity getOne(Integer id);

    List<KingEntity> getList();

    int update(KingEntity entity);

}
