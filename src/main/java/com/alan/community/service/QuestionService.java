package com.alan.community.service;

import com.alan.community.dto.PaginationDTO;
import com.alan.community.dto.QuestionDTO;
import com.alan.community.exception.CustomizeErrorCode;
import com.alan.community.exception.CustomizeException;
import com.alan.community.mapper.QuestionExtMapper;
import com.alan.community.mapper.QuestionMapper;
import com.alan.community.mapper.UserMapper;
import com.alan.community.model.Question;
import com.alan.community.model.QuestionExample;
import com.alan.community.model.User;
import com.alan.community.model.UserExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
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
    @Autowired
    private QuestionExtMapper questionExtMapper;

    public PaginationDTO queryAllQuestion(PaginationDTO paginationDTO,Integer creatorId) {
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        PageHelper.startPage(paginationDTO.getCurrentPage(),paginationDTO.getPageSize());
        QuestionExample questionExample = new QuestionExample();
        List<Question> questionList;
        if (creatorId != null) {
            questionExample.createCriteria().andCreatorIdEqualTo(creatorId);
        }
        questionList= questionMapper.selectByExampleWithBLOBs(questionExample);
        int total = (int) new PageInfo<>(questionList).getTotal();
        for (Question question : questionList) {
            User user = userMapper.selectByPrimaryKey(question.getCreatorId());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setTotalRows(total);
        paginationDTO.setQuestionDTOS(questionDTOS);
        return paginationDTO;
    }

    public QuestionDTO queryById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question==null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.selectByPrimaryKey(question.getCreatorId());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return  questionDTO;
    }

    public void addQuestion(Question question) {
        questionMapper.insert(question);
    }

    public void addOrUpdateQuestion(Question question) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            addQuestion(question);
        }
        else {
            question.setGmtModified(System.currentTimeMillis());
            int update = questionMapper.updateByPrimaryKeySelective(question);
            if (update!=1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incrViewCount(Integer id) {
        Question question=new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incrViewCount(question);

    }
}
