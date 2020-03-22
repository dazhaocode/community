package com.alan.community.service;

import com.alan.community.enums.CommentTypeEnum;
import com.alan.community.exception.CustomizeErrorCode;
import com.alan.community.exception.CustomizeException;
import com.alan.community.exception.ICustomizeErrorCode;
import com.alan.community.mapper.CommentMapper;
import com.alan.community.mapper.QuestionExtMapper;
import com.alan.community.mapper.QuestionMapper;
import com.alan.community.model.Comment;
import com.alan.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author alan
 * @date 2020/3/22 10:16
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;


    public void addComment(Comment comment) {
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        if (comment.getParentId()==null||comment.getParentId()==0) {
            throw  new CustomizeException(CustomizeErrorCode.TARGET_NOT_SELECTED);
        }
        if (comment.getType()==null||!CommentTypeEnum.isExist(comment.getType())) {
            throw  new  CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType()== CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment==null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else {
            //回复问题
            Question dbQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (dbQuestion==null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            Question question = new Question();
            question.setId(dbQuestion.getId());
            question.setCommentCount(1L);
            questionExtMapper.incrViewOrCommentOrLike(question);
        }


    }
}
