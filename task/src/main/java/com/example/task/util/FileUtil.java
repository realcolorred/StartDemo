package com.example.task.util;

import com.example.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by new on 2020/10/2.
 */
@Slf4j
public class FileUtil {

    /**
     * 获取文件夹下所有文件路径
     * @param fileFolderPath 文件夹路径
     * @return 所有文件的路径
     */
    public static List<String> readAllFile(String fileFolderPath) {
        Set<String> filePathList = new HashSet<>();
        try {
            File file = new File(fileFolderPath);
            if (file.isDirectory()) {
                //log.info("文件夹");
                String[] fileList = file.list();
                for (String aFileList : fileList) {
                    File readfile = new File(fileFolderPath + "\\" + aFileList);
                    if (!readfile.isDirectory()) {
                        //log.info("path=" + readfile.getPath());
                        //log.info("absolutepath=" + readfile.getAbsolutePath());
                        //log.info("name=" + readfile.getName());
                        filePathList.add(readfile.getAbsolutePath());
                    } else {
                        filePathList.addAll(readAllFile(fileFolderPath + "\\" + aFileList));
                    }
                }
            } else {
                //log.info("文件");
                //log.info("path=" + file.getPath());
                //log.info("absolutepath=" + file.getAbsolutePath());
                //log.info("name=" + file.getName());
            }
        } catch (Exception e) {
            log.error(" ReadExcelJob readfile Exception:", e);
        }
        return new ArrayList<>(filePathList);
    }

    /**
     * 移动文件
     * @param sourcePath 源文件路径
     * @param targetFileFolderPath 目标文件夹路径
     * @param addToStart 移动后的文件名附加内容
     * @return
     */
    public static boolean moveFile(String sourcePath, String targetFileFolderPath, String addToStart, String addToEnd) {
        File startFile = new File(sourcePath);

        // 文件夹路径+文件名+文件名附加+文件格式后缀
        String targetPath = targetFileFolderPath + getNewFileName(startFile, addToStart, addToEnd);
        File targetFile = new File(targetPath);

        log.info("开始移动文件 {} 到 {} ", sourcePath, targetFile);
        boolean success = startFile.renameTo(targetFile);
        if (success) {
            log.info("移动文件成功。从 {} 到 {} ", sourcePath, targetFile);
        } else {
            log.error("移动文件失败。从 {} 到 {} ", sourcePath, targetFile);
        }
        return success;
    }

    public static String getNewFileName(File file, String startAdd, String endAdd) {
        if (file == null) {
            return "";
        }
        if (StringUtil.isEmpty(startAdd)) {
            startAdd = "";
        }
        if (StringUtil.isEmpty(endAdd)) {
            endAdd = "";
        }
        String[] fileNames = splitSuffix(file.getName());
        return startAdd + fileNames[0] + endAdd + fileNames[1];
    }

    private static String[] splitSuffix(String fileName) {
        if (StringUtil.isBlank(fileName)) {
            return new String[] { "", "" };
        }
        if (!fileName.contains(".")) {
            return new String[] { fileName, "" };
        }
        return new String[] { fileName.substring(0, fileName.lastIndexOf(".")), fileName.substring(fileName.lastIndexOf(".")), };
    }
}
