package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by lenovo on 2019/9/25.
 */
@Getter
@Setter
public class KingEntity {

    private Integer id;

    @NotBlank(message = "名称不能为空") @Size(max = 255)
    private String kingName;

    @Size(max = 255)
    private String kingName2;

    @Pattern(regexp = "^-?[0-9]{1,4}(/{1}[0-9]{1,2}){0,2}", message = "输入时间不合法,样例为 (-)yyyy/mm/dd ")
    private String birthday;

    @Pattern(regexp = "^-?[0-9]{1,4}(/{1}[0-9]{1,2}){0,2}", message = "输入时间不合法,样例为 (-)yyyy/mm/dd ")
    private String dieday;

    @Min(value = 0) @Max(value = 200)
    private Integer age;

    @Pattern(regexp = "^-?[0-9]{1,4}(/{1}[0-9]{1,2}){0,2}", message = "输入时间不合法,样例为 (-)yyyy/mm/dd ")
    private String ruleStart;

    @Pattern(regexp = "^-?[0-9]{1,4}(/{1}[0-9]{1,2}){0,2}", message = "输入时间不合法,样例为 (-)yyyy/mm/dd ")
    private String ruleEnd;

    @Min(value = 0) @Max(value = 100)
    private Integer ruleTime;
    
    private Integer ruleStartInt;

}
