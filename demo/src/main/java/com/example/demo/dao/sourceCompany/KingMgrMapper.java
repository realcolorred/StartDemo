package com.example.demo.dao.sourceCompany;

import com.example.demo.entity.KingEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lenovo on 2019/9/25.
 */
@Repository // 方便ide-spring识别为仓储层,不加可能会报红,但是不会有编译问题
public interface KingMgrMapper {

    @Select("select * from king where id = #{id} limit 1")
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
