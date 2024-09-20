//package com.zb.service.impl;
//
//import com.zb.mapper.MatchMapper;
//import com.zb.service.MatchService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * @auther 宋亚涛
// * @verson 1.0
// */
//@Service
//public class MatchServiceimpl implements MatchService {
//    @Autowired
//    private MatchMapper matchMapper;
//
//    @Override
//    public void insert(int uploaded_id, String file_path) {
//        matchMapper.add(uploaded_id, file_path);
//    }
//
//    @Override
//    public List<String> getAllImageUrlsByUploadedId(int uploadedId) {
//        return matchMapper.getCropsByUploadedId(uploadedId);
//    }
//}
