package com.zb.controller;

import com.zb.entity.Case;
import com.zb.service.CaseService;
import com.zb.service.CropService;
import com.zb.service.UploadedService;
import com.zb.tools.AppRootPath;
import com.zb.tools.CallPad_test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

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
public class CaseController {
    @Autowired
    private CaseService caseService;
    @Autowired
    private UploadedService uploadedService;
    @Autowired
    private CropService cropService;

    @PostMapping
    public String createCase(@RequestBody Case newCase) {
        String title = newCase.getTitle();
        String description = newCase.getDescription();
        caseService.createCase(title, description);//新增案件
        return "新增案件成功";
    }

    @PostMapping("/{caseId}/upload")
    public ResponseEntity<?> uploadImage(@PathVariable("caseId") int caseId,
                                         @RequestParam("image") MultipartFile file) {
        BufferedReader in = null;

        Map<String, String> response = new HashMap<>();

        if (file.isEmpty()) { // 上传的图片文件为空
//            response.put("message", "No file uploaded");
            return new ResponseEntity<>("Invalid file", HttpStatus.BAD_REQUEST);
//            return "文件为空";
        }

        String fileName = file.getOriginalFilename();
//        D:\SWork\OCR_Demo\src\main\resources\ori\img1.png
        String file_path = AppRootPath.getappRootPath_ori() + fileName;
        File destinationFile = new File(file_path);
//        int dotIndex = fileName.lastIndexOf('.');
//        if (dotIndex > 0) {
//            fileName = fileName.substring(0, dotIndex);
//        }
//        System.out.println(fileName);
//        System.out.println(file_path);
//        return response;
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
//            Thread.sleep(600000);

            //调用python程序后，剪裁后的图片已经保存到本地文件夹
            //问题：所有结果都保存在同一个文件夹，保存到数据库怎么区分==================
//                  可以用caseID/imagename文件夹区分不同案例的不同剪裁结果 √
            //将各自案件各自图片的剪裁结果保存各自的数据库中
            int dotIndex = fileName.lastIndexOf('.');
            if (dotIndex > 0) {
                fileName = fileName.substring(0, dotIndex);
            }
            String crop_file_path = AppRootPath.getappRootPath_result() + caseId + "\\" + fileName;
            Path directoryPath = Paths.get(crop_file_path);

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath)) {
                for (Path entry : stream) {
                    if (Files.isRegularFile(entry)) {
                        // Call method to insert file path into the database
                        //获取 uploaded_id
                        int uploaded_id = uploadedService.findIdByPath(file_path);
                        cropService.createCrop(uploaded_id, entry.toString());
                    }
                }
            }


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
}

