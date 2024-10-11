package com.zb.controller;

import com.zb.Result.ResultBuilder;
import com.zb.service.UploadedService;
import com.zb.tools.CallFour;
import com.zb.tools.CallOriginalCom;
import com.zb.tools.HttpResponse;
import com.zb.tools.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@RestController
@RequestMapping("/originalcom")
@CrossOrigin//解决跨域问题
@Slf4j
public class OriginalComController {
    //比对原始图片
    //根据案件id查找出两张原始图片的地址
    //将两个地址作为两个参数传给python程序
    @Autowired
    private UploadedService uploadedService;
    @PostMapping("/show")
    public HttpResponse showOriginalCom(@RequestParam("caseId") int caseId) {
        BufferedReader in = null;
        List<String> pathByCaseId = uploadedService.findPathByCaseId(caseId);
        String firstString = pathByCaseId.get(0);
        String secondString = pathByCaseId.get(1);

        try {
            CallOriginalCom.Call(in,firstString,secondString);
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
