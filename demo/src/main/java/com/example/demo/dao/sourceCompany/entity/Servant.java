package com.example.demo.dao.sourceCompany.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author realcolorred
 * @since 2020-09-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Servant implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "servant_id", type = IdType.AUTO)
    private Long servantId;

    private String servantNameChina;

    private String servantNameEnglish;

    private String sex;

    private String weight;

    private String height;

    private Long star;


}
