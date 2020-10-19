package com.example.task.util.excel;

import com.example.common.exception.DemoException;
import com.example.common.util.DateUtil;
import com.example.common.util.NumberUtil;
import org.apache.poi.ss.usermodel.Cell;

/**
 * Created by new on 2020/10/2.
 */
public class ExcelBaseUtil {

    /**
     * 获取级联绑定公式
     * @param fatherOffSet 级联绑定的父值的列数（偏移量.比如: 0->A 1->B 2->C ... 25->Z)
     * @param fatherCol 级联绑定的父值的列数(从0开始计数)
     * @return expr
     */
    public static String getIndirectExpr(int fatherOffSet, int fatherCol) {
        String str = conventInt2Str(fatherOffSet);
        return "INDIRECT($" + str + "$" + (fatherCol + 1) + ")";
    }

    /**
     * 获取选区公式
     * @param offset 第多少列（偏移量.比如: 0->A 1->B 2->C ... 25->Z)
     * @param rowStart 开始行数(从0开始计数)
     * @param rowEnd 结束行数(从0开始计数)
     * @return expr
     */
    public static String getRangeExpr(int offset, int rowStart, int rowEnd) {
        String str = conventInt2Str(offset);
        return "$" + str + "$" + (rowStart + 1) + ":" + "$" + str + "$" + (rowEnd + 1);
    }

    /**
     * 将数值转化为excel的列表述
     * @param num 偏移量.比如: 0->A 1->B 2->C ... 25->Z
     * @return excel的列
     */
    public static String conventInt2Str(int num) {
        String str;
        if (num < 26) {
            str = String.valueOf((char) ('A' + num));
        } else if (num < 27 * 26) {
            // 参考值 26-AA 27-AB ... 51-AZ 52-BA ... 701-ZZ
            char firstChar = (char) ('A' + (num / 26 - 1));
            char lastChar = (char) ('A' + (num % 26));
            str = String.valueOf(firstChar) + String.valueOf(lastChar);
        } else {
            throw new DemoException("500", "excel行数设置过大，请检查是否正常！");
        }
        return str;
    }

    /**
     * 获取excel某个cell中的数据为字符串类型
     * @param cell value
     * @return string Value
     */
    public static String getCellFormatValue(Cell cell) {
        String cellValue;
        if (cell != null) {
            //判断cell类型
            switch (cell.getCellTypeEnum()) {
                case NUMERIC: {
                    cellValue = String.valueOf(NumberUtil.toInt(cell.getNumericCellValue()));
                    break;
                }
                case FORMULA: {
                    //判断cell是否为日期格式
                    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                        //转换为日期格式YYYY-mm-dd
                        cellValue = DateUtil.dateToString(cell.getDateCellValue());
                    } else {
                        //数字
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case STRING: {
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }
}
