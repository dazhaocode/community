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
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public PaginationDTO queryAllQuestion(PaginationDTO paginationDTO, Long creatorId) {
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        PageHelper.startPage(paginationDTO.getCurrentPage(),paginationDTO.getPageSize());
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
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
        paginationDTO.setData(questionDTOS);
        return paginationDTO;
    }

    public QuestionDTO queryById(Long id) {
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
            question.setCommentCount(0L);
            question.setViewCount(0L);
            question.setLikeCount(0L);
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

    public void incrViewOrCommentOrLikeCount(Long id) {
        Question question=new Question();
        question.setId(id);
        question.setViewCount(1L);
        questionExtMapper.incrViewOrCommentOrLike(question);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if (StringUtils.isEmptyOrWhitespace(queryDTO.getTag())) {
            return new ArrayList<>();
        }
        String regexpTag = StringUtils.replace(queryDTO.getTag(), ",", "|");
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);
        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
             BeanUtils.copyProperties(q,questionDTO);
             return  questionDTO;
        }).collect(Collectors.toList());
        return  questionDTOS;
    }
}
