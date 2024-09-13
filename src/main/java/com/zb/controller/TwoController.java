package com.zb.controller;

import com.zb.service.TwoService;
import com.zb.service.UploadedService;
import com.zb.tools.AppRootPath;
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
 * 调用Two_Dimensional_recognition.py,对识别成功的进行二维处理，
 * 处理后的结果放在two_dimensional文件夹，并且放入数据库
 */
@RestController
@RequestMapping("/case")
@CrossOrigin//解决跨域问题
@Slf4j
public class TwoController {

    @Autowired
    private TwoService twoService;
    @Autowired
    private UploadedService uploadedService;

    @PostMapping("/{caseId}/{fileName}/two")
    public ResponseEntity<?> twoDimensional(@PathVariable("caseId") int caseId,
                                            @PathVariable("fileName") String fileName) throws IOException {

        Map<String, String> response = new HashMap<>();

        BufferedReader in = null;

        //调用 Two_Dimensional_recognition.py
        try {
            CallTwo.Call(in, caseId, fileName);
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




        //将二维结果保存到数据库
//        String basePath = AppRootPath.getappRootPath_result() + caseId + "\\" + fileName + "\\" + "picture";
//        Path baseDirectoryPath = Paths.get(basePath);
//
//        try (Stream<Path> subDirectories = Files.walk(baseDirectoryPath, 1)) { // 仅遍历一级子目录
//            subDirectories.filter(Files::isDirectory).forEach(subDir -> {
//                Path twoDimensionalPath = subDir.resolve("two_dimensional");
//                if (Files.exists(twoDimensionalPath) && Files.isDirectory(twoDimensionalPath)) {
//                    try (Stream<Path> imageFiles = Files.walk(twoDimensionalPath)) {
//                        imageFiles.filter(Files::isRegularFile).forEach(entry -> {
//                            // 通过fileName 和 caseId 查找uploaded_id
//                            int uploaded_id = uploadedService.findIdByCaseIdAndName(caseId, fileName);
//                            // 处理每个图片文件
//                            twoService.insertTwo(uploaded_id, entry.toString());
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
        return DimensionToDB.toDB(caseId, fileName, uploadedService, twoService, "two_dimensional");

    }
}