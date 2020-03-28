package com.alan.community.dto;

import com.alan.community.cache.TagCache;
import lombok.Data;

import java.util.List;

/**
 * @author alan
 * @date 2020/3/24 22:09
 */
@Data
public class TagDTO {
    private String categoryName;

    private List<String> tagCaches;


}
