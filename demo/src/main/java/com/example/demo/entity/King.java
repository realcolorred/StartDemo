package com.example.demo.entity;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by lenovo on 2019/9/25.
 */
@Data
public class King {

    private Long id;

    @Size(max = 255)
    private String kingName;

    @Size(max = 255)
    private String kingName2;

    @Pattern(regexp = "^-?[0-9]{1,4}(/{1}[0-9]{1,2}){0,2}", message = "输入时间不合法,样例为 (-)yyyy/mm/dd ")
    private String birthday;

    @Pattern(regexp = "^-?[0-9]{1,4}(/{1}[0-9]{1,2}){0,2}", message = "输入时间不合法,样例为 (-)yyyy/mm/dd ")
    private String deathday;

    private Long age;

    @Pattern(regexp = "^-?[0-9]{1,4}(/{1}[0-9]{1,2}){0,2}", message = "输入时间不合法,样例为 (-)yyyy/mm/dd ")
    private String ruleStart;

    @Pattern(regexp = "^-?[0-9]{1,4}(/{1}[0-9]{1,2}){0,2}", message = "输入时间不合法,样例为 (-)yyyy/mm/dd ")
    private String ruleEnd;

    private Long ruleTime;

    private Long ruleStartInt;

}
