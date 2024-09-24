package com.zb.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public class DeleteTools {
    public static void deleteContents(Path directory) throws IOException {
        Files.list(directory).forEach(path -> {
            try {
                if (Files.isDirectory(path)) {
                    deleteContents(path); // 递归删除子目录
                }
                Files.delete(path); // 删除文件
            } catch (IOException e) {
                System.err.println("删除失败: " + path + " " + e.getMessage());
            }
        });
        Files.delete(directory); // 删除空目录
    }

    public static void deleteFolder(File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteFolder(file); // 递归删除
                }
            }
        }
        // 删除文件或空文件夹
        if (folder.delete()) {
            System.out.println("Deleted: " + folder.getAbsolutePath());
        } else {
            System.out.println("Failed to delete: " + folder.getAbsolutePath());
        }
    }
}
