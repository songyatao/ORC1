//package com.zb.service.impl;
//
//import com.zb.mapper.HistogramMapper;
//import com.zb.service.HistogramService;
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
//public class HistogramServiceimpl implements HistogramService {
//    @Autowired
//    private HistogramMapper histogramMapper;
//
//    @Override
//    public void insert(int uploaded_id, String file_path) {
//        histogramMapper.add(uploaded_id, file_path);
//    }
//
//    @Override
//    public List<String> getAllImageUrlsByUploadedId(int uploadedId) {
//        return histogramMapper.getCropsByUploadedId(uploadedId);
//    }
//}
