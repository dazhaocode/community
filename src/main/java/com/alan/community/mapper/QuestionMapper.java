package com.alan.community.mapper;


import com.alan.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface QuestionMapper {

    @Insert("insert into question (title,content,gmt_create,gmt_modified,creator_id,tag) values(#{title},#{content},#{gmtCreate},#{gmtModified},#{creatorId},#{tag})")
    int addQuestion(Question question);

    @Select("select * from question")
    List<Question> queryAllQuestion();
}
