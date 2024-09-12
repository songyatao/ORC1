package com.zb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data // 自动生成getter/setter/toString/hashCode/equals等方法
public class Case {
    @Id
    @GeneratedValue
    private UUID id;

    private String title;
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "case", cascade = CascadeType.ALL)
    private Set<Uploaded> uploadeds;

}
