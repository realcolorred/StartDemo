package com.example.demo.dao.sourceCompany;

import com.example.demo.entity.KingEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by lenovo on 2019/9/25.
 */
public interface KingMgrMapper {

    @Select("select * from king where id = #{id}")
    KingEntity getOne(Integer id);

    @Select("select * from king order by rule_start_int limit 1000")
    List<KingEntity> getList();

    int insert(KingEntity entity);

    @Delete("delete from king where id = #{id}")
    int deleteById(Integer id);

    @Delete("delete from king where id = #{id}")
    int delete(KingEntity entity);

    int update(KingEntity entity);

}
