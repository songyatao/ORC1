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
public class   ComparisonController {
    //选中两个图片，将这两个图片的url传递给后端
    //后端修改url为图片地址，传递给python程序
    //问题：前端容易做吗
    @PostMapping("/show")
    public HttpResponse showComparison(@RequestParam("imageUrl1") String imageUrl1,
                                       @RequestParam("imageUrl2") String imageUrl2){

        BufferedReader in = null;
        String local_path1 = imageUrl1.replace("http://localhost:8080/", AppRootPath.getappRootPath_static());
        String local_path2 = imageUrl2.replace("http://localhost:8080/", AppRootPath.getappRootPath_static());

        try {
            CallComparison.Call(in,local_path1,local_path2);
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
