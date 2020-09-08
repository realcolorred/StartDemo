package com.example.demo.dao.sourceCompany.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by lenovo on 2019/3/22.
 */
@Data
public class DatabaseLog {

    private Long   id;
    private String logType;
    private String logName;
    private String dbSql;
    private Date   createDate;
    private String statusCd;
    private String remark;

}
