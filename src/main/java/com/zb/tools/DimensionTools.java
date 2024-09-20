package com.zb.tools;

import com.zb.service.BaseService;
import com.zb.service.CasefileService;
import com.zb.service.UploadedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public class DimensionTools {
    public static ResponseEntity<?> toDB(int caseId, UploadedService uploadedService, BaseService service, CasefileService casefileService, String flag) {
        String basePath = AppRootPath.getappRootPath_result() + caseId + "\\" + "picture";
        Path baseDirectoryPath = Paths.get(basePath);

        try (Stream<Path> subDirectories = Files.walk(baseDirectoryPath, 1)) { // 仅遍历一级子目录
            subDirectories.filter(Files::isDirectory).forEach(subDir -> {
                Path twoDimensionalPath = subDir.resolve(flag);
                if (Files.exists(twoDimensionalPath) && Files.isDirectory(twoDimensionalPath)) {
                    try (Stream<Path> imageFiles = Files.walk(twoDimensionalPath)) {
                        imageFiles.filter(Files::isRegularFile).forEach(entry -> {

                            String file_path = entry.toString();
                            String file_name = file_path.substring(file_path.lastIndexOf("\\") + 1).replace(".jpg", "");
                            String directory = file_path.substring(0, file_path.lastIndexOf("\\two_dimensional"));
                            System.out.println(file_path);
                            int case_file_id = casefileService.getIdByPath(directory);
                            // 处理每个图片文件
                            service.insert(caseId, file_path, file_name, case_file_id);
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

    public static ResponseEntity<List<String>> toFront(BaseService service, int uploaded_id) {
        List<String> imageUrls = service.getAllImageUrlsByUploadedId(uploaded_id); // Adjust method as needed
        String baseUrl = "http://localhost:8080/";
        List<String> updatedPaths = imageUrls.stream()
                .map(path -> path.replace(AppRootPath.getappRootPath_static(), baseUrl)
                        .replace("\\", "/"))
                .collect(Collectors.toList());
        System.out.println(updatedPaths);
        return new ResponseEntity<>(updatedPaths, HttpStatus.OK);
    }
}
