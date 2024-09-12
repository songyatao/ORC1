package com.zb.tools;

import java.io.File;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public class AppRootPath {
    public static String getappRootPath_ori() {
        String appRootPath = System.getProperty("user.dir");//D:\SWork\OCR_Demo
        String filePath = appRootPath + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "ori" + File.separator;
        return filePath;
    }

    public static String getappRootPath_result() {
        String appRootPath = System.getProperty("user.dir");//D:\SWork\OCR_Demo
        String filePath = appRootPath + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "result" + File.separator;
        return filePath;
    }
    public static String getappRootPath_python() {
        String appRootPath = System.getProperty("user.dir");//D:\SWork\OCR_Demo
        String filePath = appRootPath + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "python" + File.separator;
        return filePath;
    }
}
