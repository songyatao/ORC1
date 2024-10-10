package com.zb.controller;

import com.zb.Result.ResultBuilder;
import com.zb.tools.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@RestController
@RequestMapping("/comparison")
@CrossOrigin//解决跨域问题
@Slf4j
public class ComparisonController {
    //选中两个图片，将这两个图片的url传递给后端
    //后端修改url为图片地址，传递给python程序 BiDui.py
    //问题1：前端容易实现吗
    //python生成的可操作的图片怎么传给前端，之前返回了图片的url，使用url

    //对两张图片进行对比

    //问题2：生成的可操作的图片是什么形式  弹窗是通过 OpenCV 的图形用户界面 (GUI) 创建的
    //问题3：这个可操作图片可以展示在网页上吗，最终的结果需要展示在网页上吗
    //      通常用于本地应用，而不适用于 B/S（Browser/Server）架构的 Web 应用
    @PostMapping("/show")
    public HttpResponse showComparison(@RequestParam("imageUrl1") String imageUrl1,
                                       @RequestParam("imageUrl2") String imageUrl2) {
        BufferedReader in = null;
        String local_path1 = imageUrl1.replace("http://localhost:8080/", AppRootPath.getappRootPath_static());
        String local_path2 = imageUrl2.replace("http://localhost:8080/", AppRootPath.getappRootPath_static());
        try {
            CallComparison.Call(in, local_path1, local_path2);
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


//        return DimensionTools.toDB(caseId, uploadedService, twoService, casefileService, "two_dimensional");
        return null;
    }


}
