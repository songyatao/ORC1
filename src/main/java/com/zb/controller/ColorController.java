package com.zb.controller;

import com.zb.service.ColorService;
import com.zb.service.UploadedService;
import com.zb.tools.CallColor;
import com.zb.tools.DimensionTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@RestController
@RequestMapping("/case")
@CrossOrigin//解决跨域问题
@Slf4j
public class ColorController {
    @Autowired
    private UploadedService uploadedService;
    @Autowired
    private ColorService colorService;

    @PostMapping("/{caseId}/{fileName}/color")
    public ResponseEntity<?> twoDimensional(@PathVariable("caseId") int caseId,
                                            @PathVariable("fileName") String fileName) {
        BufferedReader in = null;

        try {
            CallColor.Call(in, caseId, fileName);
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


        return DimensionTools.toDB(caseId, fileName, uploadedService, colorService, "color");

    }


}
