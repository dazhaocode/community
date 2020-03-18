package com.alan.community.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author alan
 * @date 2020/3/18 11:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    private Integer id;
    private Integer creator_id;
    private String title;
    private String content;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;

}
