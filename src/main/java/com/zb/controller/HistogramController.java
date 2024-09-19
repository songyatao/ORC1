package com.zb.controller;

import com.zb.service.HistogramService;
import com.zb.service.UploadedService;
import com.zb.tools.AppRootPath;
import com.zb.tools.CallHistogram;
import com.zb.tools.DimensionTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@RestController
@RequestMapping("/case")
@CrossOrigin//解决跨域问题
@Slf4j
public class HistogramController {
    @Autowired
    private HistogramService histogramService;
    @Autowired
    private UploadedService uploadedService;

    @PostMapping("/{caseId}/{fileName}/his")
    public ResponseEntity<?> twoDimensional(@PathVariable("caseId") int caseId,
                                            @PathVariable("fileName") String fileName) {
        BufferedReader in = null;
        try {
            CallHistogram.Call(in, caseId, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        //结果加入数据库
        try {
            String file_path = AppRootPath.getappRootPath_result() + caseId + "\\" + fileName + "\\image_statistics.png";
            int uploaded_id = uploadedService.findIdByCaseIdAndName(caseId, fileName);
            histogramService.insert(uploaded_id, file_path);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //将二位结果返回给前端
    @PostMapping("/result/{caseId}/{uploaded_id}/his")
    public ResponseEntity<List<String>> getImages(@PathVariable int uploaded_id) {
        try {
            return  DimensionTools.toFront(histogramService, uploaded_id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
