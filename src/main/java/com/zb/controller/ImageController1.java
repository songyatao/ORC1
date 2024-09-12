//package com.zb.controller;
//
//import com.zb.tools.CallPad_test;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.File;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @auther 宋亚涛
// * @verson 1.0
// */
//@RestController
//@RequestMapping("/image1")
//@CrossOrigin//解决跨域问题
////public class ImageController1 {
////
////    /**
////     * 调用pad_test.py
////     * @return
////     */
////    @GetMapping("test1")
////    public String test1(){
////        BufferedReader in = null;
////        String arg1 = "D:\\SWork\\OCR_Demo\\src\\main\\resources\\ori\\img.png";
////        String arg2 = "D:\\SWork\\OCR_Demo\\src\\main\\resources\\result";
////        try{
////            String[] args1 = new String[]{"python","D:\\SWork\\python\\pad_test.py",arg1,arg2};
////            Process proc = Runtime.getRuntime().exec(args1);
////            in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"gbk"));
////            StringBuilder sb = new StringBuilder();
////            String line = null;
////            while((line = in.readLine())!=null){
////                sb.append(line);
////            }
////            proc.waitFor();
////            System.out.println(sb.toString());
////        } catch (Exception e){
////            e.printStackTrace();
////        }finally {
////            if(in != null){
////                try {
////                    in.close();
////                } catch (IOException e) {
////                    throw new RuntimeException(e);
////                }
////            }
////        }
////
////        return "ImageController1 执行完毕";
////
////    }
////
////}
//
//
//public class ImageController1 {
//
//    /**
//     * 调用pad_test.py
//     *
//     * @return
//     */
//    @PostMapping("/upload")
//    public ResponseEntity<Map<String, String>> handleFileUpload(@PathVariable("caseId") String caseId,
//                                                                @RequestParam("image") MultipartFile file) {
//        BufferedReader in = null;
//
//        Map<String, String> response = new HashMap<>();
//
//        if (file.isEmpty()) { // 上传的图片文件为空
//            response.put("message", "No file uploaded");
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//
//        String fileName = file.getOriginalFilename();
//        File destinationFile = new File("D:\\SWork\\OCR_Demo\\src\\main\\resources\\ori\\" + fileName);
//
//        try {
//            file.transferTo(destinationFile); // 保存图片
//
//            //调用python程序
////            String arg1 = "D:\\SWork\\OCR_Demo\\src\\main\\resources\\ori\\" + fileName;
////            String arg2 = "D:\\SWork\\OCR_Demo\\src\\main\\resources\\result";
////            String[] args1 = new String[]{"python","D:\\SWork\\python\\pad_test.py",arg1,arg2};
////            Process proc = Runtime.getRuntime().exec(args1);
////            in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"gbk"));
////            StringBuilder sb = new StringBuilder();
////            String line = null;
////            while((line = in.readLine())!=null){
////                sb.append(line);
////            }
////            proc.waitFor();
////            System.out.println(sb.toString());
//
//            CallPad_test.Call(fileName, in, caseId);//调用python程序
//
//            //调用python程序后，剪裁后的图片已经
//
//            response.put("message", "File uploaded successfully");
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.put("message", "Error saving file");
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
//    }
//
//}
