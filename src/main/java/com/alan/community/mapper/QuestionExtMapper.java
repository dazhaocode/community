package com.alan.community.mapper;

import com.alan.community.model.Question;
import com.alan.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionExtMapper {

    void incrViewCount(Question question);
}