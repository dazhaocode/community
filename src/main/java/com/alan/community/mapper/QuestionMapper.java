package com.alan.community.mapper;


import com.alan.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface QuestionMapper {

    @Insert("insert into question (title,content,gmt_create,gmt_modified,creator_id,tag) values(#{title},#{content},#{gmtCreate},#{gmtModified},#{creator_id},#{tag})")
    int addQuestion(Question question);
}
