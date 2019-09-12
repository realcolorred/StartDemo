package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by lenovo on 2019/3/22.
 */
@Getter
@Setter
public class DblogEntity {

    private Long   id;
    private String logType;
    private String logName;
    private String dbSql;
    private Date   createDate;
    private String statusCd;
    private String remark;

}
