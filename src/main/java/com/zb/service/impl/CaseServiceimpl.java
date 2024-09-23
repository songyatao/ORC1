package com.zb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zb.entity.Cases;
import com.zb.mapper.CaseMapper;
import com.zb.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Service
public class CaseServiceimpl extends ServiceImpl<CaseMapper, Cases> implements CaseService {

    @Autowired
    CaseMapper caseMapper;

    @Override
    public int add(String title, String description) {

        Cases newCase = new Cases();
        newCase.setTitle(title);
        newCase.setDescription(description);

        // 保存到数据库并返回生成的 ID
        caseMapper.insert(newCase); // 假设这个方法会插入数据
        return newCase.getId(); // 获取生成的 ID
    }
}
