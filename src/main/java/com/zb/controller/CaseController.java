package com.zb.controller;

import com.zb.Result.Result;
import com.zb.entity.Case;
import com.zb.entity.Crop;
import com.zb.service.CaseService;
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
//            response.put("message", "No file uploaded");
            return new ResponseEntity<>("Invalid file", HttpStatus.BAD_REQUEST);
//            return "文件为空";
        }

        String fileName = file.getOriginalFilename();
//        D:\SWork\OCR_Demo\src\main\resources\static\images\ori\img1.png
        String file_path = AppRootPath.getappRootPath_ori() + fileName;
        File destinationFile = new File(file_path);

        try {
            file.transferTo(destinationFile); // 保存图片到本地
            //保存图片到数据库==================
            uploadedService.createUpload(caseId, file_path);


            //调用python程序
//            String arg1 = "D:\\SWork\\OCR_Demo\\src\\main\\resources\\ori\\" + fileName;
//            String arg2 = "D:\\SWork\\OCR_Demo\\src\\main\\resources\\result";
//            String[] args1 = new String[]{"python","D:\\SWork\\python\\pad_test.py",arg1,arg2};
//            Process proc = Runtime.getRuntime().exec(args1);
//            in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"gbk"));
//            StringBuilder sb = new StringBuilder();
//            String line = null;
//            while((line = in.readLine())!=null){
//                sb.append(line);
//            }
//            proc.waitFor();
//            System.out.println(sb.toString());

            CallPad_test.Call(fileName, in, caseId);//调用python程序

            //调用python程序后，剪裁后的图片已经保存到本地文件夹
            //问题：所有结果都保存在同一个文件夹，保存到数据库怎么区分==================
//                  可以用caseID/imagename文件夹区分不同案例的不同剪裁结果 √
            //将各自案件各自图片的剪裁结果保存各自的数据库中

            cropTool = new CropTool();
            cropTool.CropToDB(fileName, caseId, uploadedService, cropService);


//            response.put("message", "File uploaded successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
//            System.out.println("123456");
//            return "图片上传成功，并且成功执行了pad_test.py";

        } catch (Exception e) {
            e.printStackTrace();
//            response.put("message", "Error saving file");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//            return "异常";
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


    //返回识别成功的结果给前端
    @PostMapping("/{caseId}/results")
    public ResponseEntity<List<String>> getResults(@RequestParam int uploadedId) {

        List<String> imageUrls = cropService.getCropsByUploadedId(uploadedId);
        String baseUrl = "http://localhost:8080/";
        List<String> updatedPaths = imageUrls.stream()
                .map(path -> path.replace("D:\\SWork\\OCR_Demo\\src\\main\\resources\\static\\", baseUrl))
                .collect(Collectors.toList());
        System.out.println(updatedPaths);
        return new ResponseEntity<>(updatedPaths, HttpStatus.OK);
    }

    //下载识别成功的word文档
    @GetMapping("/result/{caseId}/{fileName}/downloadWord")
    public ResponseEntity<InputStreamResource> downloadWord(@PathVariable("caseId") int caseId,
                                                            @PathVariable("fileName") String fileName) {
        // 本地文件路径，请根据实际文件路径修改
        String filePath = AppRootPath.getappRootPath_result() + caseId + "\\" + fileName + "\\picture";
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

}

