//package com.zb.controller;
//
//import com.zb.service.MatchService;
//import com.zb.service.UploadedService;
//import com.zb.tools.AppRootPath;
//import com.zb.tools.CallMatch;
//import com.zb.tools.DimensionTools;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Stream;
//
///**
// * @auther 宋亚涛
// * @verson 1.0
// */
//@RestController
//@RequestMapping("/case")
//@CrossOrigin//解决跨域问题
//@Slf4j
//public class MatchController {
//
//    @Autowired
//    private UploadedService uploadedService;
//    @Autowired
//    private MatchService matchService;
//
//    @PostMapping("/{caseId}/{fileName}/match")
//    public ResponseEntity<?> twoDimensional(@PathVariable("caseId") int caseId,
//                                            @PathVariable("fileName") String fileName) throws IOException {
//        BufferedReader in = null;
//        Map<String, String> response = new HashMap<>();
//        //调用 match.py
//        try {
////            String[] args1 = new String[]{"python", AppRootPath.getappRootPath_python() + "match.py", arg1, arg2,arg3};
////            Process proc = Runtime.getRuntime().exec(args1);
////            in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "gbk"));
////            StringBuilder sb = new StringBuilder();
////            String line = null;
////            while ((line = in.readLine()) != null) {
////                sb.append(line);
////            }
////            proc.waitFor();
////            System.out.println(sb.toString());
//            CallMatch.Call(in, caseId, fileName);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        } finally {
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//
//
//        // 将match的结果放入数据库
//        String basePath = AppRootPath.getappRootPath_result() + caseId + "\\" + fileName + "\\" + "match";
//        Path baseDirectoryPath = Paths.get(basePath);
//
//        try (Stream<Path> subDirectories = Files.walk(baseDirectoryPath, 1)) { // 仅遍历一级子目录
//            subDirectories.filter(Files::isDirectory).forEach(subDir -> {
//                Path twoDimensionalPath = subDir.resolve("");
//                if (Files.exists(twoDimensionalPath) && Files.isDirectory(twoDimensionalPath)) {
//                    try (Stream<Path> imageFiles = Files.walk(twoDimensionalPath)) {
//                        imageFiles.filter(Files::isRegularFile).forEach(entry -> {
//                            // 通过fileName 和 caseId 查找uploaded_id
//                            int uploaded_id = uploadedService.findIdByCaseIdAndName(caseId, fileName);
//                            // 处理每个图片文件
//                            matchService.insert(uploaded_id, entry.toString());
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
//
//    }
//
//    @PostMapping("/result/{caseId}/{uploaded_id}/match")
//    public ResponseEntity<List<String>> getImages(@PathVariable int uploaded_id) {
//        try {
////            List<String> imageUrls = twoService.getAllImageUrlsByUploadedId(uploaded_id); // Adjust method as needed
////            String baseUrl = "http://localhost:8080/";
////            List<String> updatedPaths = imageUrls.stream()
////                    .map(path -> path.replace("D:\\SWork\\OCR_Demo\\src\\main\\resources\\static\\", baseUrl))
////                    .collect(Collectors.toList());
//////            System.out.println(updatedPaths);
////            return new ResponseEntity<>(updatedPaths, HttpStatus.OK);
//            return  DimensionTools.toFront(matchService, uploaded_id);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//}
//
