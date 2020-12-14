package com.example.demo.exp1;

/**
 * Created by new on 2020/10/27.
 */
public class PrintBean {

    private Long    id;
    private boolean isResult;
    private String  leftParamType;
    private String  leftParam;
    private String  RightParamType;
    private String  RightParam;
    private String  expr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isResult() {
        return isResult;
    }

    public void setIsResult(boolean isResult) {
        this.isResult = isResult;
    }

    public String getLeftParamType() {
        return leftParamType;
    }

    public void setLeftParamType(String leftParamType) {
        this.leftParamType = leftParamType;
    }

    public String getLeftParam() {
        return leftParam;
    }

    public void setLeftParam(String leftParam) {
        this.leftParam = leftParam;
    }

    public String getRightParamType() {
        return RightParamType;
    }

    public void setRightParamType(String rightParamType) {
        RightParamType = rightParamType;
    }

    public String getRightParam() {
        return RightParam;
    }

    public void setRightParam(String rightParam) {
        RightParam = rightParam;
    }

    public String getExpr() {
        return expr;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }
}
