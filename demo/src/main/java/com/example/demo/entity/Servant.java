package com.example.demo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class Servant {

    private Long   servantId;
    private String servantNameChina;
    private String servantNameEnglish;
    private String sex;
    private String weight;
    private String height;
    private Long   star;

}
