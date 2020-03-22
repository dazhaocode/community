package com.alan.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author alan
 * @date 2020/3/22 9:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private Long parentId;
    private String content;
    private Long type;
}
