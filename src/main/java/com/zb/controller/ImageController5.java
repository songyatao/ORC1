//package com.zb.controller;
//
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
//@RequestMapping("/image5")
//@CrossOrigin //解决跨域问题
//public class ImageController5 {
//    /**
//     * 调用Color_Result.py
//     * @return
//     */
//    @GetMapping("test5")
//    public String test5() {
//        BufferedReader in = null;
//        String arg1 = "D:\\SWork\\OCR_Demo\\src\\main\\resources\\result\\Test";
////        String arg2 = "D:\\SWork\\OCR_Demo\\src\\main\\resources\\result\\Test\\Color_Result";
//        try {
//            String[] args1 = new String[]{"python","D:\\SWork\\python\\COLOR.py", arg1};
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
//        return "ImageController5 执行完毕";
//    }
//}
