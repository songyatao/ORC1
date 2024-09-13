package com.zb.controller;

import com.zb.tools.CallColor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@RestController
@RequestMapping("/case")
@CrossOrigin//解决跨域问题
@Slf4j
public class ColorController {
    @PostMapping("/{caseId}/{fileName}/color")
    public ResponseEntity<?> twoDimensional(@PathVariable("caseId") int caseId,
                                            @PathVariable("fileName") String fileName) {
        BufferedReader in = null;

        try {
            CallColor.Call(in,caseId,fileName);
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
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
