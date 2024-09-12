package com.zb.controller;

import com.zb.service.FourService;
import com.zb.service.UploadedService;
import com.zb.tools.CallFour;
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
public class FourController {

    @Autowired
    private FourService fourService;
    @Autowired
    private UploadedService uploadedService;

    @PostMapping("/{caseId}/{fileName}/four")
    public ResponseEntity<?> twoDimensional(@PathVariable("caseId") int caseId,
                                            @PathVariable("fileName") String fileName) {
        BufferedReader in = null;
        Map<String, String> response = new HashMap<>();

        try {
            CallFour.Call(in, caseId, fileName);
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

        //将四维结果保存到数据库
        String basePath = "src/main/resources/result/" + caseId + "/" + fileName + "/picture";
        Path baseDirectoryPath = Paths.get(basePath);

        try (Stream<Path> subDirectories = Files.walk(baseDirectoryPath, 1)) { // 仅遍历一级子目录
            subDirectories.filter(Files::isDirectory).forEach(subDir -> {
                Path twoDimensionalPath = subDir.resolve("four_dimensional");
                if (Files.exists(twoDimensionalPath) && Files.isDirectory(twoDimensionalPath)) {
                    try (Stream<Path> imageFiles = Files.walk(twoDimensionalPath)) {
                        imageFiles.filter(Files::isRegularFile).forEach(entry -> {
                            // 通过fileName 和 caseId 查找uploaded_id
                            int uploaded_id = uploadedService.findIdByCaseIdAndName(caseId, fileName);
                            // 处理每个图片文件
                            fourService.insertFour(uploaded_id, entry.toString());
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
