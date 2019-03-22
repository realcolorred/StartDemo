package com.example.demo.dao;

import com.example.demo.entity.DblogEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by lenovo on 2019/3/22.
 */
@Repository("myFixLogMapper")
public interface MyFixLogMapper {

    @Select("select * from dblog where id = #{id}")
    DblogEntity getOne(Long id);
}
