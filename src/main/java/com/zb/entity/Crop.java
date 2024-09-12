package com.zb.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public class Crop {
    @Id
    @GeneratedValue
    private UUID id;

    private int uploaded_id;

    private String file_path;
}
