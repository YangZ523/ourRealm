package com.ourrealm.boys.YZ.utils;


import java.util.Date;

/**
 * Created by make it on 2015/9/17.
 */
public class FileUtil {
    /**
     * 获取文件后缀名
     *
     * @param oldFileName
     * @return
     */
    public static String getSuffix(String oldFileName) {
        int n = oldFileName.lastIndexOf(".");
        return oldFileName.substring(n, oldFileName.length());
    }

    /**
     * 获取文件名
     *
     * @param filePath 如http://oss.hongbaok.com/online/aa.jpg
     * @return
     */
    public static String getFileName(String filePath) {
        int n = filePath.lastIndexOf("/");
        return filePath.substring(n + 1, filePath.length());
    }

    /**
     * 按时间生成一个文件名 yyyyMMddHHmmss+5位随机数
     *
     * @return
     */
    public static String generateName(String oldFileName) {
        String suffix = getSuffix(oldFileName);
        String newName = DateUtil.format(new Date(), "yyyyMMddHHmmss") + GenerationCodeUtil.GenerateNumber() + suffix;
        return newName;
    }


}
