package com.zb.service;

import com.zb.entity.Crop;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public interface CropService {
    Crop createCrop(int uploaded_id, String file_path);
}
