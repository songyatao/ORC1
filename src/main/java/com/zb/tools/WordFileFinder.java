package com.zb.tools;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public class WordFileFinder {
    public static String findWordFile(String directoryPath) {
        // 创建 File 对象
        File directory = new File(directoryPath);

        // 确保目录存在
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("目录不存在或不是一个有效的目录");
        }

        // 查找 Word 文件的过滤器
        FilenameFilter wordFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".doc") || name.endsWith(".docx");
            }
        };

        // 获取目录中的所有 Word 文件
        File[] wordFiles = directory.listFiles(wordFilter);

        if (wordFiles != null && wordFiles.length > 0) {
            // 返回第一个找到的 Word 文件的路径
            return wordFiles[0].getAbsolutePath();
        } else {
            throw new RuntimeException("没有找到 Word 文件");
        }
    }
}
