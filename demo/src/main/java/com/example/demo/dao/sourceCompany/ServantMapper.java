package com.example.demo.dao.sourceCompany;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Servant;
//import org.springframework.stereotype.Repository;

//@Repository // 方便ide识别为仓储层,不加可能会报红,但是不会有编译问题
public interface ServantMapper extends BaseMapper<Servant> {

    /*@Select("SELECT * FROM servant WHERE servant_id = #{id}")
    Servant getOneById(Long id);

    @Delete("DELETE FROM servant WHERE servant_id =#{id}")
    int deleteById(Long id);

    @Insert("insert into servant(servant_name_china) values (#{name})")
    int insertByName(String name);*/
}
