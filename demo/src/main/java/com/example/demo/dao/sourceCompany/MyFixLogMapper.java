package com.example.demo.dao.sourceCompany;

import com.example.demo.entity.DblogEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lenovo on 2019/3/22.
 */
@Repository("myFixLogMapper")
public interface MyFixLogMapper {

    @Select("select * from dblog where create_date > DATE_ADD(now(),INTERVAL - 10 day) ORDER BY create_date desc limit 100 ")
    List<DblogEntity> getDefault();

    @Select("select * from dblog where id = #{id}")
    DblogEntity getOne(Long id);
}
