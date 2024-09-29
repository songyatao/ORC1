package com.zb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zb.entity.Cases;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public interface CaseService extends IService<Cases> {
    int add(String title, String description);//新增
    List<Cases> getAll();

}
