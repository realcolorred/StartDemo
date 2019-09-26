package com.example.demo.dao.sourceCompany;

import com.example.demo.entity.DblogEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lenovo on 2019/3/22.
 */
@Repository // 方便ide识别为仓储层,不加可能会报红,但是不会有编译问题
public interface MyFixLogMapper {

    @Select("select * from dblog where create_date > DATE_ADD(now(),INTERVAL - 300 day) ORDER BY create_date desc limit 100 ")
    List<DblogEntity> getDefault();

    @Select("select * from dblog where id = #{id}")
    DblogEntity getOne(Long id);
}
