package com.zb.controller;

import com.zb.entity.Cases;
import com.zb.tools.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */

@RestController
@RequestMapping("/test")
@CrossOrigin//解决跨域问题
@Slf4j
public class test {
    @GetMapping("/add")
    public String add(@RequestBody Cases newCase){

        System.out.println("123");
        return "123";
    }
}

