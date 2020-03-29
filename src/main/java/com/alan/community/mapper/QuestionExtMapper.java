package com.alan.community.mapper;

import com.alan.community.dto.SearchDTO;
import com.alan.community.model.Question;
import com.alan.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionExtMapper {

    void incrViewOrCommentOrLike(Question question);

    List<Question> selectRelated(Question question);

    List<Question> selectBySearch(SearchDTO searchDTO);
}