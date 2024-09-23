package com.zb.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public class Uploaded {

    private Integer id;
    private int case_id;

    private LocalDateTime uploaded_at;

    private String file_path;

    private String file_name;

}
