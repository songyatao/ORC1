package com.zb.tools;

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
    public void CropToDB(String fileName, int caseId, UploadedService uploadedService, CropService cropService) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            fileName = fileName.substring(0, dotIndex);
        }
        String crop_file_path = AppRootPath.getappRootPath_result() + caseId + "\\" + fileName + "\\" + "picture";

        Path directoryPath = Paths.get(crop_file_path);


        try (Stream<Path> subDirs = Files.list(directoryPath)) {
            List<Path> imageFiles = subDirs
                    .filter(Files::isDirectory)
                    .flatMap(dir -> {
                        try {
                            return Files.list(dir).filter(Files::isRegularFile).filter(this::isImageFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return Stream.empty();
                        }
                    })
                    .collect(Collectors.toList());

            for (Path imageFile : imageFiles) {
                int uploadedId = uploadedService.findIdByCaseIdAndName(caseId, fileName);
                cropService.createCrop(uploadedId, imageFile.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean isImageFile(Path path) {
        String fileName = path.getFileName().toString().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif");
    }
}
