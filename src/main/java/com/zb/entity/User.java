package com.zb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data // 自动生成getter/setter/toString/hashCode/equals等方法
public class User {
    private Integer id;
    private String username;
    private String password;
}
