package com.alan.community.cache;

import com.alan.community.dto.TagDTO;
import org.thymeleaf.util.StringUtils;
import sun.security.krb5.internal.crypto.NullEType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author alan
 * @date 2020/3/24 22:11
 */
public class TagCache {

    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO backEnd = new TagDTO();
        backEnd.setCategoryName("后端");
        backEnd.setTagCaches(Arrays.asList("php","java","node.js","python","c++","c","golang","spring","springboot","flask","c#","swoole","ruby","asp.net","ruby-on-rails","scala","rust","lavarel","爬虫"));
        tagDTOS.add(backEnd);

        TagDTO frontEnd = new TagDTO();
        frontEnd.setCategoryName("前端");
        frontEnd.setTagCaches(Arrays.asList("java","script","前端","vue.js","css","html","html5","node.js","react.js","jquery","css3","es6","typescript","chrome","npm","bootstrap","sass","less","chrome-devtools","firefox","angular","coffee","script","safari","postcss","postman","fiddler","yarn","webkit","firebug","edge"));
        tagDTOS.add(frontEnd);
        return tagDTOS;
    }



    public static String filterInvalid(String tags){

        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOS = get();
        List<String> tagList = tagDTOS.stream().flatMap(tagDTO -> tagDTO.getTagCaches().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));


        return invalid;
    }
}
