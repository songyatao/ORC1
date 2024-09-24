package com.zb.tools;

import com.zb.service.CasefileService;
import com.zb.service.CropService;
import com.zb.service.UploadedService;

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
public class CropTool {
    public void CropToDB(String fileName, int caseId, UploadedService uploadedService, CropService cropService, CasefileService casefileService) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            fileName = fileName.substring(0, dotIndex);
        }
        String crop_file_path = AppRootPath.getappRootPath_result() + caseId + "\\" + "picture";
        Path directoryPath = Paths.get(crop_file_path);

        try (Stream<Path> subDirs = Files.list(directoryPath)) {
            String finalFileName = fileName;
            List<Path> imageFiles = subDirs
                    .filter(Files::isDirectory)
                    .flatMap(dir -> {
                        String folderName = dir.getFileName().toString(); // 获取子文件夹名字
                        try {
                            return Files.list(dir)
                                    .filter(Files::isRegularFile)
                                    .filter(this::isImageFile)
                                    .peek(imageFile -> {
                                        int uploadedId = uploadedService.findIdByCaseIdAndName(caseId, finalFileName);
                                        // 在此处将子文件夹名和图像文件存入数据库
                                        int id = casefileService.getIdByNameAndCaseId(folderName,caseId);
                                        String filePath = imageFile.toString();
                                        String image_name = filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.lastIndexOf("."));
                                        cropService.createCrop(uploadedId, filePath, image_name, id, caseId); // 将子文件夹名作为参数
                                    });
                        } catch (IOException e) {
                            e.printStackTrace();
                            return Stream.empty();
                        }
                    })
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isImageFile(Path path) {
        String fileName = path.getFileName().toString().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif");
    }
}
