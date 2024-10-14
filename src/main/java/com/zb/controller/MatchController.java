package com.zb.controller;

import com.zb.Result.ResultBuilder;
import com.zb.service.MatchService;
import com.zb.service.UploadedService;
import com.zb.tools.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@RestController
@RequestMapping("/match")
@CrossOrigin//解决跨域问题
@Slf4j
public class MatchController {

    @Autowired
    private UploadedService uploadedService;
    @Autowired
    private MatchService matchService;

    @PostMapping("/add/{caseId}")
    public HttpResponse match(@PathVariable("caseId") int caseId) throws IOException {

        List<?> matchFiles = matchService.getAll(caseId);
        if (matchFiles == null || matchFiles.isEmpty()) {
            BufferedReader in = null;
            Map<String, String> response = new HashMap<>();
            //调用 match.py
            try {

                CallMatch.Call(in, caseId);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultBuilder.faile(ResultCode.CODE_ERROR);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }


//         将match的结果放入数据库
            String basePath = AppRootPath.getappRootPath_result() + caseId + "\\" + "match";
            Path baseDirectoryPath = Paths.get(basePath);

            try (Stream<Path> subDirectories = Files.walk(baseDirectoryPath, 1)) { // 仅遍历一级子目录
                subDirectories.filter(Files::isDirectory).forEach(subDir -> {
                    Path twoDimensionalPath = subDir.resolve("");
                    if (Files.exists(twoDimensionalPath) && Files.isDirectory(twoDimensionalPath)) {
                        try (Stream<Path> imageFiles = Files.walk(twoDimensionalPath)) {
                            imageFiles.filter(Files::isRegularFile).forEach(entry -> {
                                // 处理每个图片文件
                                matchService.insert(caseId, entry.toString());
                            });
                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                    }
                });

                return ResultBuilder.successNoData(ResultCode.SAVE_SUCCESS);
            } catch (IOException e) {
                e.printStackTrace();
                return ResultBuilder.faile(ResultCode.CODE_ERROR);
            }
        } else {
            System.out.println("已经存在，不重复添加");
            return ResultBuilder.successNoData(ResultCode.SAVE_SUCCESS);
        }


    }

    @PostMapping("/result/{caseId}")
    public HttpResponse<List<String>> getImages(@PathVariable int caseId) {
        List<String> imageUrls = matchService.getPathByCaseId(caseId);
        String baseUrl = "http://localhost:8080/";
        List<String> updatedPaths = imageUrls.stream()
                .map(path -> path.replace(AppRootPath.getappRootPath_static(), baseUrl)
                        .replace("\\", "/"))
                .collect(Collectors.toList());
        System.out.println(updatedPaths);
        return ResultBuilder.success(updatedPaths, ResultCode.QUERY_SUCCESS);
    }
}

