package com.example.demo.exp1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by new on 2020/10/27.
 */
public class ExpTest {

    public static void main(String[] ages) {
        List<RowBean> input = getInput();
        System.out.println(getExpr(input));
        System.out.println(checkResult(input));
        System.out.println(getResult(input));
    }

    private static List<PrintBean> getResult(List<RowBean> rowBeans) {
        Stack<String> stack = new Stack<>();
        int yxj = 0;
        Stack<RowBean> stackValue = new Stack<>();

        for (int i = rowBeans.size() - 1; i >= 0; i++) {
            RowBean rowBean = rowBeans.get(i);

            switch (rowBean.getLeftBracket()) {
                case "(":
                    stack.push("(");
                    break;
                case "((":
                    stack.push("(");
                    stack.push("(");
                    break;
                case ")":
                    stack.push(")");
                    break;
                case "))":
                    stack.push(")");
                    stack.push(")");
                    break;
            }

            stack.push("VALUE");

            switch (rowBean.getRightBracket()) {
                case "(":
                    stack.push("(");
                    break;
                case "((":
                    stack.push("(");
                    stack.push("(");
                    break;
                case ")":
                    stack.push(")");
                    break;
                case "))":
                    stack.push(")");
                    stack.push(")");
                    break;
            }

            switch (rowBean.getLogicOperator()) {
                case "and":
                    stack.push("and");
                    break;
                case "or":
                    stack.push("or");
                    break;
            }

        }
        return null;
    }

    private static int pushToStack(Stack<String> stack, Stack<RowBean> stackRow, Stack<PrintBean> stackMix, String value, RowBean valueBean,
                                   List<PrintBean> printBeans, int yxj) {
        // (1 or 2) and (3 or 4)
        // ((1 and 2) or 3 ) or (4 and 5) and 6

        // 如果非运算符，则直接入栈
        if ("VALUE".equals(value)) {
            stack.push(value);
            stackRow.push(valueBean);
            return yxj;
        } else if ("(".equals(value)) {
            stack.push(value);
            return yxj;
        } else if (")".equals(value)) {
            String topValue = stack.peek();
            while(!topValue.equals("(")){

            }
        }


        return 0;
    }

    private static int getYXJ(String key) {
        Map<String, Integer> yxj = new HashMap<>();
        //yxj.put("(", 1);
        //yxj.put(")", 2);
        yxj.put("or", 3);
        yxj.put("and", 4);

        if (yxj.containsKey(key)) {
            return yxj.get(key);
        } else {
            return -1;
        }
    }

    private static String getExpr(List<RowBean> rowBeans) {
        StringBuilder expr = new StringBuilder();
        for (RowBean rowBean : rowBeans) {
            expr.append(" ");
            expr.append(rowBean.getLeftBracket());
            expr.append(" ");
            expr.append(rowBean.getValue());
            expr.append(" ");
            expr.append(rowBean.getRightBracket());
            expr.append(" ");
            expr.append(rowBean.getLogicOperator());
            expr.append(" ");
        }
        return expr.toString().replaceAll("\\s+", " ").trim();
    }

    private static String checkResult(List<RowBean> rowBeans) {
        int count = 0;
        for (int i = 0; i < rowBeans.size(); i++) {
            RowBean rowBean = rowBeans.get(i);
            switch (rowBean.getLeftBracket()) {
                case "(":
                    count = count + 1;
                    break;
                case "((":
                    count = count + 2;
                    break;
                case ")":
                    count = count - 1;
                    break;
                case "))":
                    count = count - 2;
                    break;
            }
            if (count < 0)
                return "括号不平衡";
            switch (rowBean.getRightBracket()) {
                case "(":
                    count = count + 1;
                    break;
                case "((":
                    count = count + 2;
                    break;
                case ")":
                    count = count - 1;
                    break;
                case "))":
                    count = count - 2;
                    break;
            }
            if (count < 0)
                return "括号不平衡";

            if (i == (rowBeans.size() - 1)) {
                if (!rowBean.getLogicOperator().equals("")) {
                    return "行尾不需要运算符号";
                }
                if (count != 0) {
                    return "括号不平衡";
                }
            } else {
                if (rowBean.getLogicOperator().equals("")) {
                    return "项间需要运算符";
                }
            }
        }
        return "无问题";
    }

    private static List<RowBean> getInput() {
        List<RowBean> input = new ArrayList<>();
        input.add(new RowBean("H1", "", "", "and"));
        input.add(new RowBean("H2", "(", "", "or"));
        input.add(new RowBean("H3", "", ")", ""));
        return input;
    }
}
