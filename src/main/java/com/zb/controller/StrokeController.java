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
@RequestMapping("/stroke")
@CrossOrigin//解决跨域问题
@Slf4j
public class StrokeController {
    @PostMapping("/add/{caseId}")
    public HttpResponse stroke(@PathVariable("caseId") int caseId) {
        BufferedReader in = null;

        try {
            CallStroke.Call(in, caseId);
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


        //保存到数据库
//        return DimensionTools.toDB(caseId, uploadedService, twoService, casefileService, "two_dimensional");
        return null;
    }
}
