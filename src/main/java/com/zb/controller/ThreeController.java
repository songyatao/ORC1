package com.zb.controller;

import com.zb.service.BaseService;
import com.zb.service.ThreeService;
import com.zb.service.UploadedService;
import com.zb.tools.AppRootPath;
import com.zb.tools.CallThree;
import com.zb.tools.CallTwo;
import com.zb.tools.DimensionToDB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@RestController
@RequestMapping("/case")
@CrossOrigin//解决跨域问题
@Slf4j
public class ThreeController {
    @Autowired
    private BaseService threeService;
    @Autowired
    private UploadedService uploadedService;

    @PostMapping("/{caseId}/{fileName}/three")
    public ResponseEntity<?> twoDimensional(@PathVariable("caseId") int caseId,
                                            @PathVariable("fileName") String fileName) {

        Map<String, String> response = new HashMap<>();

        BufferedReader in = null;

        //调用 Two_Dimensional_recognition.py
        try {
            CallThree.Call(in, caseId, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        //将三维结果保存到数据库
//        String basePath = AppRootPath.getappRootPath_result() + caseId + "\\" + fileName + "\\" + "picture";
//        Path baseDirectoryPath = Paths.get(basePath);
//
//        try (Stream<Path> subDirectories = Files.walk(baseDirectoryPath, 1)) { // 仅遍历一级子目录
//            subDirectories.filter(Files::isDirectory).forEach(subDir -> {
//                Path twoDimensionalPath = subDir.resolve("three_dimensional");
//                if (Files.exists(twoDimensionalPath) && Files.isDirectory(twoDimensionalPath)) {
//                    try (Stream<Path> imageFiles = Files.walk(twoDimensionalPath)) {
//                        imageFiles.filter(Files::isRegularFile).forEach(entry -> {
//                            // 通过fileName 和 caseId 查找uploaded_id
//                            int uploaded_id = uploadedService.findIdByCaseIdAndName(caseId, fileName);
//                            // 处理每个图片文件
//                            threeService.insertThree(uploaded_id, entry.toString());
//                        });
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }

        return DimensionToDB.toDB(caseId, fileName, uploadedService, threeService,"three_dimensional");

    }
}
