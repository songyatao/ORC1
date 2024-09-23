package com.zb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data // 自动生成getter/setter/toString/hashCode/equals等方法
public class Casefile {

    private Integer id;

    private String file_name;
    private String file_path;
    private int case_id;
    private int uploaded_id;
}
