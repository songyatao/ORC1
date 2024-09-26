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
@RequestMapping("/scale")
@CrossOrigin//解决跨域问题
@Slf4j
public class ScaleController {
    //图片放缩
    //1. 前端首先访问 /casefile/{caseId}/load 生成文件夹按钮
    //2. 点击文件夹按钮后访问 /crop/{caseId}/{case_file_id}/load 返回图片url，在页面上显示图片
    //3. 点击图片，访问此接口，将图片的url传入并修改为图片地址，将此地址作为参数传入python程序

    //展示图片放缩
    @PostMapping("/show")
    public HttpResponse showScale(@RequestParam("imageUrl") String imageUrl) {
        BufferedReader in = null;
        String local_path = imageUrl.replace("http://localhost:8080/", AppRootPath.getappRootPath_static());
        try {
            CallScale.Call(in, local_path);
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

        return ResultBuilder.successNoData(ResultCode.SAVE_SUCCESS);
    }
}
