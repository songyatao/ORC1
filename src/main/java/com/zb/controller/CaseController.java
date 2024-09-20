package com.zb.controller;

import com.zb.Result.Result;
import com.zb.entity.Case;
import com.zb.entity.Crop;
import com.zb.service.CaseService;
import com.zb.service.CasefileService;
import com.zb.service.CropService;
import com.zb.service.UploadedService;
import com.zb.tools.AppRootPath;
import com.zb.tools.CallPad_test;
import com.zb.tools.CropTool;
import com.zb.tools.WordFileFinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @auther 宋亚涛
 * @verson 1.0
 * createCase 新增案件
 * uploadImage 调用pad_test.py，上传图片，完成对图片的剪裁，
 * 剪裁结束后对剪裁后的结果进行识别，
 * 识别成功的放进picture，失败的放进unrecognized
 * 上传的图片入库
 */
@RestController
@RequestMapping("/case")
@CrossOrigin//解决跨域问题
@Slf4j
public class CaseController {
    @Autowired
    private CaseService caseService;
    @Autowired
    private UploadedService uploadedService;
    @Autowired
    private CropService cropService;
    @Autowired
    CasefileService casefileService;
    private Map<Integer, Integer> uploadCounts = new HashMap<>();

    private CropTool cropTool = null;

    //新增案件
    @PostMapping
    public String createCase(@RequestBody Case newCase) {
        String title = newCase.getTitle();
        String description = newCase.getDescription();
        caseService.createCase(title, description);//新增案件
        return "新增案件成功";
    }

    //上传图片，剪裁，并把原始图片和识别成功的结果保存进数据库
    @PostMapping("/{caseId}/upload")
    public ResponseEntity<?> uploadImage(@PathVariable("caseId") int caseId,
                                         @RequestParam("image") MultipartFile file) throws IOException, InterruptedException {
        BufferedReader in = null;

        Map<String, String> response = new HashMap<>();


        if (file.isEmpty()) { // 上传的图片文件为空
            return new ResponseEntity<>("Invalid file", HttpStatus.BAD_REQUEST);
        }

        String fileName = file.getOriginalFilename();
        String file_path = AppRootPath.getappRootPath_ori() + fileName;
        File destinationFile = new File(file_path);

        try {
            file.transferTo(destinationFile); // 保存上传的图片到本地
            //保存上传的图片到数据库
            uploadedService.createUpload(caseId, file_path);


            CallPad_test.Call(fileName, in, caseId);//调用python程序,剪裁后的图片保存到相应案件的文件夹中


            // 增加上传计数
            uploadCounts.put(caseId, uploadCounts.getOrDefault(caseId, 0) + 1);

            if (uploadCounts.get(caseId) == 2) {

                //把结果的文件夹名字以及地址保存到数据库
                String directoryPath = AppRootPath.getappRootPath_result() + caseId + "\\picture";
                File directory = new File(directoryPath);
                if (directory.isDirectory()) {
                    File[] subfolders = directory.listFiles(File::isDirectory);
                    if (subfolders != null) {
                        for (File subfolder : subfolders) {
                            String folderName = subfolder.getName();
                            String folderPath = subfolder.getAbsolutePath();
                            casefileService.insert(folderName, folderPath, caseId);//文件名，文件路径入库
                        }
                    }
                } else {
                    System.out.println("Provided path is not a directory.");
                }
                cropTool = new CropTool();
                cropTool.CropToDB(fileName, caseId, uploadedService, cropService, casefileService);
                // 重置计数
                uploadCounts.put(caseId, 0);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);

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
    }

    //下载识别成功的word文档
    @GetMapping("/result/{caseId}/downloadWord")
    public ResponseEntity<InputStreamResource> downloadWord(@PathVariable("caseId") int caseId) {
        // 本地文件路径，请根据实际文件路径修改
        String filePath = AppRootPath.getappRootPath_result() + caseId + "\\picture";
        String result = WordFileFinder.findWordFile(filePath);

        System.out.println(result);
        try {
            // 创建文件对象
            File file = new File(result);

            // 创建文件输入流
            FileInputStream fis = new FileInputStream(file);

            // 设置响应头，指定文件名为"document.docx"
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=document.docx");

            // 返回文件的输入流资源
            return new ResponseEntity<>(new InputStreamResource(fis), headers, HttpStatus.OK);

        } catch (IOException e) {
            // 出现异常时返回500内部服务器错误
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //点击“显示结果”按钮，把文件夹名字返回给前端，前端把这些名字做成按钮
    @GetMapping("/{caseId}/results")
    public ResponseEntity<List<String>> createFileNameButton(@PathVariable("caseId") int caseId) {
        List<String> fileNames = casefileService.getNames(caseId);
        return ResponseEntity.ok(fileNames); // 返回fileNames
    }

    @GetMapping("/{caseId}/{case_file_id}/results")
    public ResponseEntity<List<String>> createFileNameButton(@PathVariable("caseId") int caseId,
                                                             @PathVariable("case_file_id") int id) {
        List<String> imageUrls = cropService.getCropsByCaseIdAndFileId(caseId, id);
        String baseUrl = "http://localhost:8080/";
        List<String> updatedPaths = imageUrls.stream()
                .map(path -> path.replace(AppRootPath.getappRootPath_static(), baseUrl)
                        .replace("\\", "/"))
                .collect(Collectors.toList());
        System.out.println(updatedPaths);
        return ResponseEntity.ok(updatedPaths);
    }
}

