package com.example.task.util.excel;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by new on 2020/9/27.
 */
@Data
@Builder
public class SheetBean {

    // sheet名称
    private String sheetName;

    // 第一行显示的名称提示
    private List<String> rowNames;

    // 行列选值限制
    private List<RowLimitBean> rowLimitBeanList;

    public void addRowLimitBen(List<String> rowNameList, String targetRowName, List<String> rowSelect) {
        if (rowLimitBeanList == null) {
            rowLimitBeanList = new ArrayList<>();
        }
        rowLimitBeanList.add(new RowLimitBean(rowNameList, targetRowName, rowSelect));
    }

    public void addRowLimitBen(List<String> rowNameList, String targetRowName, String dependRowName, Map<String, List<String>> complexRowSelect) {
        if (rowLimitBeanList == null) {
            rowLimitBeanList = new ArrayList<>();
        }
        rowLimitBeanList.add(new RowLimitBean(rowNameList, targetRowName, dependRowName, complexRowSelect));
    }
}
