package com.alan.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author alan
 * @date 2020/3/17 11:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiteeUser {
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;

}
