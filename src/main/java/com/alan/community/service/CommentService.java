package com.alan.community.service;

import com.alan.community.dto.dbCommentDTO;
import com.alan.community.enums.CommentTypeEnum;
import com.alan.community.enums.NotificationEnum;
import com.alan.community.enums.NotificationTypeEnum;
import com.alan.community.exception.CustomizeErrorCode;
import com.alan.community.exception.CustomizeException;
import com.alan.community.mapper.*;
import com.alan.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author alan
 * @date 2020/3/22 10:16
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void addComment(Comment comment,User currentUser) {
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
            comment.setCommentCount(0);
            comment.setLikeCount(0L);
            commentMapper.insert(comment);
            dbComment.setCommentCount(1);
            commentExtMapper.incrViewOrCommentOrLike(dbComment);
            //创建评论回复通知
            createNotify(comment, dbComment.getCommentator(), currentUser.getName(),comment.getContent(), NotificationTypeEnum.REPLY_COMMENT, dbComment.getParentId());

        }else {
            //回复问题
            Question dbQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (dbQuestion==null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            comment.setLikeCount(0L);
            comment.setCommentCount(0);
            commentMapper.insert(comment);
            Question question = new Question();
            question.setId(dbQuestion.getId());
            question.setCommentCount(1L);
            questionExtMapper.incrViewOrCommentOrLike(question);
            //创建问题通知
            createNotify(comment, dbQuestion.getCreatorId(),currentUser.getName(),dbQuestion.getTitle(), NotificationTypeEnum.REPLY_QUESTION, dbQuestion.getId());
        }
    }

    private void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationTypeEnum, Long outerId) {
        if (comment.getCommentator().equals(receiver)) {
            return;
        }
        Notification notification=new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setNotifier(comment.getCommentator());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterid(outerId);
        notification.setOuterTitle(outerTitle);
        notification.setStatus(NotificationEnum.UNREAD.getStatus());
        notification.setType(notificationTypeEnum.getType());
        notification.setOuterid(comment.getParentId());
        notificationMapper.insert(notification);
    }

    public List<dbCommentDTO> queryAllByTargetId(Long id, CommentTypeEnum typeEnum) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(typeEnum.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExampleWithBLOBs(commentExample);
        if (comments.size()==0) {
            return new ArrayList<>();
        }
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long>userIds=new ArrayList<>();
        userIds.addAll(commentators);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        List<dbCommentDTO> commentDTOS = comments.stream().map(comment -> {
            dbCommentDTO dbCommentDTO = new dbCommentDTO();
            BeanUtils.copyProperties(comment,dbCommentDTO);
            dbCommentDTO.setUser(userMap.get(comment.getCommentator()));
            return dbCommentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }

    public void incrLike(Long id) {
        Comment comment = new Comment();
        comment.setLikeCount(1L);
        comment.setId(id);
        commentExtMapper.incrViewOrCommentOrLike(comment);
    }
}
