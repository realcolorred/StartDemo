package com.example.task.util.excel;

import com.example.common.exception.DemoException;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * Created by new on 2020/9/27.
 */
@Getter
public class RowLimitBean {

    // 限制位置（列，从0开始计算位置）
    private int rowIndex;
    // 依赖项的坐标（列，从0开始计算位置）
    private int dependIndex = -1;

    // 简单可选值 (dependIndex非-1的时候设置)
    private List<String>              rowSelect;
    // 复杂可选值(依赖值，依赖值级联的可选值)
    private Map<String, List<String>> complexRowSelect;

    public RowLimitBean(List<String> rowNameList, String targetRowName, List<String> rowSelect) {
        int targetRowIndex = -1;
        for (int i = 0; i < rowNameList.size(); i++) {
            if (rowNameList.get(i).equals(targetRowName)) {
                if (targetRowIndex != -1) {
                    throw new DemoException("500", "设置excel下拉框错误，列名称存在重复：" + targetRowName);
                }
                targetRowIndex = i;
            }
        }
        if (targetRowIndex == -1) {
            throw new DemoException("500", "设置excel下拉框错误，选择了不存在的列：" + targetRowName);
        }
        this.rowIndex = targetRowIndex;
        this.rowSelect = rowSelect;
    }

    public RowLimitBean(List<String> rowNameList, String targetRowName, String dependRowName, Map<String, List<String>> complexRowSelect) {
        int targetRowId = -1;
        int dependRowId = -1;
        for (int i = 0; i < rowNameList.size(); i++) {
            if (rowNameList.get(i).equals(targetRowName)) {
                if (targetRowId != -1) {
                    throw new DemoException("500", "设置excel下拉框级联错误，列名称存在重复：" + targetRowName);
                }
                targetRowId = i;
            }
            if (rowNameList.get(i).equals(dependRowName)) {
                if (dependRowId != -1) {
                    throw new DemoException("500", "设置excel下拉框级联错误，父列名称存在重复：" + dependRowName);
                }
                dependRowId = i;
            }
        }
        if (targetRowId == -1) {
            throw new DemoException("500", "设置excel下拉框级联错误，选择了不存在的列：" + targetRowName);
        }
        if (dependRowId == -1) {
            throw new DemoException("500", "设置excel下拉框级联错误，选择了不存在的父列：" + dependRowName);
        }
        this.rowIndex = targetRowId;
        this.dependIndex = dependRowId;
        this.complexRowSelect = complexRowSelect;
    }

}
