package com.zb.tools;

import com.zb.service.BaseService;
import com.zb.service.UploadedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public class DimensionToDB {
    public static ResponseEntity<?> toDB(int caseId, String fileName, UploadedService uploadedService, BaseService service, String flag) {
        String basePath = AppRootPath.getappRootPath_result() + caseId + "\\" + fileName + "\\" + "picture";
        Path baseDirectoryPath = Paths.get(basePath);

        try (Stream<Path> subDirectories = Files.walk(baseDirectoryPath, 1)) { // 仅遍历一级子目录
            subDirectories.filter(Files::isDirectory).forEach(subDir -> {
                Path twoDimensionalPath = subDir.resolve(flag);
                if (Files.exists(twoDimensionalPath) && Files.isDirectory(twoDimensionalPath)) {
                    try (Stream<Path> imageFiles = Files.walk(twoDimensionalPath)) {
                        imageFiles.filter(Files::isRegularFile).forEach(entry -> {
                            // 通过fileName 和 caseId 查找uploaded_id
                            int uploaded_id = uploadedService.findIdByCaseIdAndName(caseId, fileName);
                            // 处理每个图片文件
                            service.insert(uploaded_id, entry.toString());
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
