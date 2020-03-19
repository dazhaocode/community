package com.alan.community.service;

import com.alan.community.dto.PaginationDTO;
import com.alan.community.dto.QuestionDTO;
import com.alan.community.mapper.QuestionMapper;
import com.alan.community.mapper.UserMapper;
import com.alan.community.model.Question;
import com.alan.community.model.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alan
 * @date 2020/3/18 13:36
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO queryAllQuestion(PaginationDTO paginationDTO,Integer id) {
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        PageHelper.startPage(paginationDTO.getCurrentPage(),paginationDTO.getPageSize());
        List<Question> questionList = questionMapper.queryAllQuestion(id);
        int total = (int) new PageInfo<>(questionList).getTotal();
        for (Question question : questionList) {
            User user = userMapper.selectByUserId(question.getCreatorId());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setTotalRows(total);
        paginationDTO.setQuestionDTOS(questionDTOS);
        return paginationDTO;
    }


}
