package com.example.demo.dao.sourceCompany;

import com.example.demo.entity.ServantEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository // 方便ide识别为仓储层,不加可能会报红,但是不会有编译问题
public interface ServantMapper {

    @Select("SELECT * FROM servant WHERE servant_id = #{id}")
    ServantEntity getOne(Long id);

    @Delete("DELETE FROM servant WHERE servant_id =#{id}")
    int delete(Long id);

    @Insert("insert into servant(servant_name_china) values (#{name})")
    int insert(String name);
}