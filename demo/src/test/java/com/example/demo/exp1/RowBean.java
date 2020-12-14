package com.example.demo.exp1;

/**
 * Created by new on 2020/10/27.
 */
public class RowBean {

    private String value;
    private String leftBracket; // 连接符号-左
    private String rightBracket; // 连接符号-右
    private String logicOperator; // 连接符号-项间

    public RowBean(String value, String leftBracket, String rightBracket, String logicOperator) {
        this.value = value;
        this.leftBracket = leftBracket;
        this.rightBracket = rightBracket;
        this.logicOperator = logicOperator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLeftBracket() {
        return leftBracket;
    }

    public void setLeftBracket(String leftBracket) {
        this.leftBracket = leftBracket;
    }

    public String getRightBracket() {
        return rightBracket;
    }

    public void setRightBracket(String rightBracket) {
        this.rightBracket = rightBracket;
    }

    public String getLogicOperator() {
        return logicOperator;
    }

    public void setLogicOperator(String logicOperator) {
        this.logicOperator = logicOperator;
    }
}
