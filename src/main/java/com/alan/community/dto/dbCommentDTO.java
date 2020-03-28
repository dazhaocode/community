package com.alan.community.dto;

import com.alan.community.model.User;
import lombok.Data;

/**
 * @author alan
 * @date 2020/3/23 12:45
 */
@Data
public class dbCommentDTO {

    private Long id;
    private Long parentId;
    private Long type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer commentCount;
    private String content;
    private User user;

}
