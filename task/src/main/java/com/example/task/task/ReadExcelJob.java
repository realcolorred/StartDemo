package com.example.task.task;

import com.example.common.exception.DemoException;
import com.example.common.request.ApiRespResult;
import com.example.common.util.CollectionUtil;
import com.example.common.util.DateUtil;
import com.example.demoapi.api.ServantApi;
import com.example.task.task.base.BaseJob;
import com.example.task.util.FileUtil;
import com.example.task.util.excel.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by new on 2020/9/25.
 */
@Slf4j
@Component
@DisallowConcurrentExecution // 此标记用在实现Job的类上面,意思是不允许并发执行
public class ReadExcelJob extends BaseJob<String> {

    @Value("${spring.demo.job.status.ReadExcelJob:close}")
    private String status;

    @Value("${job.excel.path:F:\\env\\doc}")
    private String excelFileFolderPath;

    @Value("${job.excel.path.done:F:\\env\\doc_done\\}")
    private String excelFileFolderPathDone;

    @Autowired
    private ServantApi servantApi;

    @Scheduled(fixedRate = 20 * 1000)//每20秒执行一次
    public void test() {
        if ("open".equals(status)) {
            super.execute();
        }
    }

    @Override
    public List<String> getTasks() {
        List<String> list = FileUtil.readAllFile(excelFileFolderPath);
        List<String> newList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(list)) {
            list.forEach(file -> {
                if (file.endsWith(".xls") || file.endsWith("xlsx")) {
                    newList.add(file);
                }
            });
        }
        return newList;
    }

    @Override
    public void doTask(String filePath) {
        log.info("开始读取文档内容:" + filePath);
        Workbook wb = ExcelUtil.readExcel(filePath);
        List<String> resultList = new ArrayList<>();
        if (wb != null) {
            Map<Integer, List<List<String>>> wbData = ExcelUtil.getWorkBookData(wb, true);
            if (!wbData.containsKey(0)) {
                throw new DemoException("500", "excel数据错误，请分两个sheet依次输入模板基本设置，模板属性设置");
            }
            List<List<String>> servantInfoList = wbData.get(0);
            for (List<String> info : servantInfoList) {
                ApiRespResult<String> respResult = servantApi
                    .insertData(info.get(0), info.get(1), info.get(2), info.get(3), info.get(4), info.get(5));
                if (respResult.isSuccess()) {
                    resultList.add("数据新增成功!" + respResult.getData());
                    log.info("数据新增成功!" + respResult.getData());
                } else {
                    resultList.add(respResult.getMessage());
                    log.warn(respResult.getMessage());
                }
            }

            // 请求结束，将文档移入其他文件夹并更名
            String addToStart = DateUtil.dateToString(new Date(), DateUtil.YYYYMMDDHHMMSSSSS) + "_source_";
            String addToEnd = "";
            FileUtil.moveFile(filePath, excelFileFolderPathDone, addToStart, addToEnd);

            // 生成处理结果文档
            try {
                // 结果写入wb
                wb = ExcelUtil.writeHandleResultWebBook(wb, resultList);

                addToStart = DateUtil.dateToString(new Date(), DateUtil.YYYYMMDDHHMMSSSSS) + "_result_";
                String resultFileName = FileUtil.getNewFileName(new File(filePath), addToStart, addToEnd);

                FileOutputStream fileOut = new FileOutputStream(excelFileFolderPathDone + resultFileName);
                wb.write(fileOut);
                wb.close();
                fileOut.close();
            } catch (IOException e) {
                log.warn("", e);
            }
        }
    }
}
