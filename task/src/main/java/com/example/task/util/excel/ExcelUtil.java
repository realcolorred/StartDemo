package com.example.task.util.excel;

import com.example.common.util.CollectionUtil;
import com.example.common.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetVisibility;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by new on 2020/9/27.
 *
 * excel工具类
 */
@Slf4j
public class ExcelUtil {

    /**
     * 创建一个excel
     * @param modelSet data
     * @return result
     */
    public static HSSFWorkbook getWorkBookModel(List<SheetBean> modelSet) {
        int minColWidth = 12; // 列最小宽度
        int maxColWith = 30; // 列最大宽度
        int selectedRowStart = 1; //从第x行设置下拉列表(从零开始计数)
        int selectedRowEnd = 99; //第x行结束设置下拉列表(从零开始计数)
        String hiddenSheetNameStart = "HiddenSheet";
        String nameNameStart = "NAME";

        if (CollectionUtil.isEmpty(modelSet))
            return null;

        /**
         * 1.创建一个webbook，对应一个Excel文件
         */
        HSSFWorkbook workbook = new HSSFWorkbook();

        for (int sheetCount = 0; sheetCount < modelSet.size(); sheetCount++) {
            SheetBean sheetBean = modelSet.get(sheetCount);

            /**
             * 2.在webbook中添加一个sheet,对应Excel文件中的sheet
             */
            HSSFSheet sheet = workbook.createSheet(sheetBean.getSheetName());

            /**
             * 3.创建单元格，并设置值表头 设置表头居中
             */
            HSSFCellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND); // 填充单元格

            /**
             * 4.赋值 对标题赋值
             */
            List<String> listLabel = sheetBean.getRowNames();
            if (listLabel != null && listLabel.size() > 0) {
                HSSFRow row = sheet.createRow(0);
                for (int i = 0; i < listLabel.size(); i++) {
                    HSSFCell cell = row.createCell(i); // 创建第0行的第i个格子.
                    cell.setCellType(CellType.STRING); // 设置类型为字符串
                    cell.setCellValue(listLabel.get(i)); // 设值
                    int width = listLabel.get(i).getBytes().length;
                    if (width < minColWidth) {
                        width = minColWidth;
                    }
                    if (width > maxColWith) {
                        width = maxColWith;
                    }
                    sheet.setColumnWidth(i, width * 300); // 设宽
                }
            }

            HSSFSheet hiddenSheet = null;
            if (CollectionUtil.isNotEmpty(sheetBean.getRowLimitBeanList())) {
                for (int hiddenColIndex = 0; hiddenColIndex < sheetBean.getRowLimitBeanList().size(); hiddenColIndex++) {
                    RowLimitBean limitBean = sheetBean.getRowLimitBeanList().get(hiddenColIndex);

                    if (limitBean.getDependIndex() == -1) {
                        /**
                         * 1.创建隐藏的提供选值的sheet
                         */
                        String hiddenSheetName = hiddenSheetNameStart + sheetCount;
                        if (hiddenSheet == null) {
                            hiddenSheet = workbook.createSheet(hiddenSheetName); // 创建隐藏域
                        }
                        for (int strNum = 0; strNum < limitBean.getRowSelect().size(); strNum++) {
                            HSSFRow row = hiddenSheet.getRow(strNum);
                            if (row == null) {
                                row = hiddenSheet.createRow(strNum);
                            }
                            row.createCell(hiddenColIndex).setCellValue(limitBean.getRowSelect().get(strNum));
                        }

                        /**
                         * 2.绑定选值sheet 选定数据提取域
                         */
                        // 设置一个选值区名称，后面使用这个名称进行绑定
                        String nameName = nameNameStart + UUIDUtil.getUUID();
                        // 选值区 （隐藏sheet的列数，开始行，结束行）
                        String formulaText =
                            hiddenSheetName + "!" + ExcelBaseUtil.getRangeExpr(hiddenColIndex, 0, limitBean.getRowSelect().size() - 1);
                        Name name = workbook.createName();
                        name.setNameName(nameName);
                        name.setRefersToFormula(formulaText);

                        // 需要选值下拉框的列
                        int cellNum = limitBean.getRowIndex();
                        // 设置excel中实际的第n列的的2-100行为下拉列表
                        CellRangeAddressList addressList = new CellRangeAddressList(selectedRowStart, selectedRowEnd, cellNum, cellNum);

                        // 绑定选值域与选值
                        DataValidationHelper helper = sheet.getDataValidationHelper();
                        DataValidationConstraint validationConstraint = helper.createFormulaListConstraint(nameName);
                        DataValidation dataValidation = helper.createValidation(validationConstraint, addressList);
                        sheet.addValidationData(dataValidation);
                    } else {
                        /**
                         * 1.创建隐藏的提供选值的sheet
                         */
                        String hiddenSheetName = hiddenSheetNameStart + sheetCount;
                        if (hiddenSheet == null) {
                            hiddenSheet = workbook.createSheet(hiddenSheetName); // 创建隐藏域
                        }

                        int rowIndex = 0;
                        for (String fatherName : limitBean.getComplexRowSelect().keySet()) {
                            int startRowIndex = rowIndex;
                            // 将选值全部设置入某一列单元格
                            List<String> select = limitBean.getComplexRowSelect().get(fatherName);
                            if (CollectionUtil.isNotEmpty(select)) {
                                for (String value : select) {
                                    HSSFRow row = hiddenSheet.getRow(rowIndex);
                                    if (row == null) {
                                        row = hiddenSheet.createRow(rowIndex);
                                    }
                                    row.createCell(hiddenColIndex).setCellValue(value);

                                    rowIndex++;
                                }

                                // 将同一父节点的选值区注册为一个name
                                String formulaText = hiddenSheetName + "!" + ExcelBaseUtil.getRangeExpr(hiddenColIndex, startRowIndex, rowIndex - 1);
                                Name name = workbook.createName();
                                name.setNameName(fatherName);
                                name.setRefersToFormula(formulaText);
                            }
                        }

                        /**
                         * 绑定级联关系
                         */
                        // 需要选值下拉框的列
                        int cellNum = limitBean.getRowIndex();
                        int fathetCellNum = limitBean.getDependIndex();
                        // 设置第cellNum列的的tempInt行为下拉列表
                        for (int tempInt = selectedRowStart; tempInt <= selectedRowEnd; tempInt++) {
                            CellRangeAddressList addressList = new CellRangeAddressList(tempInt, tempInt, cellNum, cellNum);

                            // 绑定选值域与选值
                            DataValidationHelper helper = sheet.getDataValidationHelper();
                            DataValidationConstraint validationConstraint = helper
                                .createFormulaListConstraint(ExcelBaseUtil.getIndirectExpr(fathetCellNum, tempInt));
                            DataValidation dataValidation = helper.createValidation(validationConstraint, addressList);
                            sheet.addValidationData(dataValidation);
                        }
                    }
                }
            }
        }

        // 最后将以HIDDEN开头的sheet进行隐藏
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            String name = workbook.getSheetName(i);
            if (name.startsWith(hiddenSheetNameStart)) {
                workbook.setSheetHidden(i, true);
            }
        }
        return workbook;
    }

    /**
     * 获取指定excel的workBook数据
     * @param filePath excel路径
     * @return workbook
     */
    public static Workbook readExcel(String filePath) {
        if (filePath == null) {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (".xls".equals(extString)) {
                return new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                return new XSSFWorkbook(is);
            } else {
                return null;
            }
        } catch (FileNotFoundException e) {
            log.info("FileNotFoundException", e);
        } catch (IOException e) {
            log.info("IOException", e);
        }
        return null;
    }

    /**
     * 将workBook数据中的数据读取为Map格式
     * @param workbook workbook
     * @return result
     */
    public static Map<Integer, List<List<String>>> getWorkBookData(Workbook workbook, boolean ignoreFirstRow) {
        if (workbook == null) {
            return null;
        }
        int numOfSheets = workbook.getNumberOfSheets();
        if (numOfSheets < 1) {
            return null;
        }

        // 设置可视sheet的id
        int availableSheetId = 0;
        Map<Integer, List<List<String>>> workBookData = new HashMap<>();
        for (int sheetIndex = 0; sheetIndex < numOfSheets; sheetIndex++) {
            if (workbook.getSheetVisibility(sheetIndex) != SheetVisibility.VISIBLE) {
                continue;
            }

            Sheet sheet = workbook.getSheetAt(sheetIndex);
            log.info("开始读取sheet:" + sheet.getSheetName());
            //获取最大行数
            List<List<String>> sheetData = new ArrayList<>();
            int rowNum = sheet.getPhysicalNumberOfRows();
            int startReadRow = ignoreFirstRow ? 1 : 0;
            if (rowNum > startReadRow) {
                //获取第一行
                Row row = sheet.getRow(0);
                //获取最大列数
                int colNum = row.getPhysicalNumberOfCells();
                for (int i = startReadRow; i < rowNum; i++) {
                    List<String> rowData = new ArrayList<>();
                    row = sheet.getRow(i);
                    for (int j = 0; j < colNum; j++) {
                        rowData.add(ExcelBaseUtil.getCellFormatValue(row.getCell(j)));
                    }
                    log.info("第" + (i + 1) + "行的数据为" + rowData);
                    sheetData.add(rowData);
                }
            }
            workBookData.put(availableSheetId, sheetData);
            availableSheetId++;
        }
        return workBookData;
    }

    /**
     * 改写结果进入workBook
     * @param workbook
     * @param resultList
     * @return
     */
    public static Workbook writeHandleResultWebBook(Workbook workbook, List<String> resultList) {
        if (workbook == null)
            return null;

        int numOfSheets = workbook.getNumberOfSheets();
        if (numOfSheets < 1)
            return workbook;

        Sheet sheet = null;
        for (int sheetIndex = 0; sheetIndex < numOfSheets; sheetIndex++) {
            if (workbook.getSheetVisibility(sheetIndex) == SheetVisibility.VISIBLE) {
                log.info("选中了第x个sheet:" + sheetIndex);
                sheet = workbook.getSheetAt(sheetIndex);
                log.info("sheet名称为:" + sheet.getSheetName());
                break;
            }
        }
        if (sheet == null)
            return workbook;

        int rowNum = sheet.getPhysicalNumberOfRows();//获取最大行数
        log.info("sheet最大行数为:" + rowNum);
        if (rowNum > 0) {
            Row row = sheet.getRow(0);//获取第一行
            int colNum = row.getPhysicalNumberOfCells();//获取最大列数
            for (int i = 0; i < rowNum; i++) {
                row = sheet.getRow(i);
                Cell cell = row.createCell(colNum);
                cell.setCellType(CellType.STRING);
                if (i == 0) {
                    cell.setCellValue("处理结果");
                } else {
                    if (resultList.size() > (i - 1)) {
                        cell.setCellValue(resultList.get(i - 1));
                    }
                }
            }
        }
        return workbook;
    }
}
