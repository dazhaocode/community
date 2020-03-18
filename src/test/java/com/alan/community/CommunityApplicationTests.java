package com.alan.community;

import com.alan.community.dto.QuestionDTO;
import com.alan.community.mapper.UserMapper;
import com.alan.community.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CommunityApplicationTests {
    @Autowired
   private UserMapper userMapper;
    private QuestionService questionService;

    @Test
    void contextLoads() {

        List<QuestionDTO> questionList=questionService.queryAllQuestion();
        for (QuestionDTO questionDTO : questionList) {
            System.out.println(questionDTO.getUser().getAvatarUrl());
        }
    }

}
