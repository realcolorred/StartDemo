package com.example.demo.entity;

public class ServantEntity {

    private Long servantId;
    private String servantNameChina;
    private String servantNameEnglish;
    private String sex;
    private String weight;
    private String height;
    private Long star;

    public Long getServantId() {
        return servantId;
    }

    public void setServantId(Long servantId) {
        this.servantId = servantId;
    }

    public String getServantNameChina() {
        return servantNameChina;
    }

    public void setServantNameChina(String servantNameChina) {
        this.servantNameChina = servantNameChina;
    }

    public String getServantNameEnglish() {
        return servantNameEnglish;
    }

    public void setServantNameEnglish(String servantNameEnglish) {
        this.servantNameEnglish = servantNameEnglish;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public Long getStar() {
        return star;
    }

    public void setStar(Long star) {
        this.star = star;
    }
}
