package com.alan.community.mapper;

import com.alan.community.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentExtMapper {

    void incrViewOrCommentOrLike(Comment comment);
}