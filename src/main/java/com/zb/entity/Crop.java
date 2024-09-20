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
    private String file_name;
    private int case_file_id;
    private int case_id;
}
