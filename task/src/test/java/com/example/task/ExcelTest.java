package com.example.task;

import com.example.common.util.UUIDUtil;
import com.example.task.task.ReadExcelJob;
import com.example.task.util.excel.ExcelUtil;
import com.example.task.util.excel.SheetBean;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by new on 2020/9/27.
 */
public class ExcelTest {

    @Test
    public void test() {
        ReadExcelJob readExcelJob = new ReadExcelJob();
        readExcelJob.doTask("F:\\env\\doc\\6b91c8d9226841e1abae34aae6e5707e.xls");
    }

    @Test
    public void test2() throws IOException {
        List<String> rowNameList1 = Arrays.asList("prod_id", "prod_name", "模板名称", "业务大类", "业务类型", "对应标准名称", "优惠列表方案类型", "是否催单提醒", "催单提醒期限（天）");
        SheetBean sheetBean1 = SheetBean.builder().sheetName("模板基本设置").rowNames(rowNameList1).build();
        //sheetBean1.addRowLimitBen(rowNameList1, "业务大类", );
        //sheetBean1.addRowLimitBen(rowNameList1, "业务类型", "业务大类", );
        //sheetBean1.addRowLimitBen(rowNameList1, "对应标准名称", "业务类型", );

        List<String> rowNameList2 = Arrays.asList("序号", "属性编码", "属性名称", "属性类型", "属性数据类型", "属性输入类型", "属性取值类型", "属性取值(英文逗号隔开)");
        SheetBean sheetBean2 = SheetBean.builder().sheetName("模板属性设置").rowNames(rowNameList2).build();

        List<SheetBean> modelSet = new ArrayList<>();
        modelSet.add(sheetBean1);
        modelSet.add(sheetBean2);
        HSSFWorkbook workbook = ExcelUtil.getWorkBookModel(modelSet);

        FileOutputStream fileOut;
        fileOut = new FileOutputStream("F:\\" + UUIDUtil.getUUID() + ".xls");
        if (workbook != null) {
            workbook.write(fileOut);
            workbook.close();
        }
        fileOut.close();
        System.out.println("excel生成成功!");
    }

}
