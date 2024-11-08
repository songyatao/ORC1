//package com.zb.controller;
//
//import com.zb.tools.AppRootPath;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
///**
// * @auther 宋亚涛
// * @verson 1.0
// */
//@RestController
//@RequestMapping("/image6")
//@CrossOrigin //解决跨域问题
//public class ImageController6 {
//    /**
//     * 调用match.py
//     *
//     * @return
//     */
//    @GetMapping("test6")
//    public String test6() {
//        BufferedReader in = null;
//        String arg1 = "D:\\SWork\\OCR_Demo\\src\\main\\resources\\static\\images";
//        String arg2 = "D:\\SWork\\OCR_Demo\\src\\main\\resources\\static\\images\\result\\1\\img1\\picture";
//        String arg3 = "result\\1\\img1";
//        try {
//            String[] args1 = new String[]{"python", AppRootPath.getappRootPath_python() + "match.py", arg1, arg2,arg3};
//            Process proc = Runtime.getRuntime().exec(args1);
//            in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "gbk"));
//            StringBuilder sb = new StringBuilder();
//            String line = null;
//            while ((line = in.readLine()) != null) {
//                sb.append(line);
//            }
//            proc.waitFor();
//            System.out.println(sb.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
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
//        return "ImageController6 执行完毕";
//    }
//}
