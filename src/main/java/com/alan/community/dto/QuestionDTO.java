package com.alan.community.dto;

import com.alan.community.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author alan
 * @date 2020/3/18 13:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
    private Long id;
    private Long creatorId;
    private String title;
    private String content;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long commentCount;
    private Long viewCount;
    private Long likeCount;
    private User user;
}
