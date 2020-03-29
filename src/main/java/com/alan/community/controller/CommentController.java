package com.alan.community.controller;

import com.alan.community.dto.CommentDTO;
import com.alan.community.dto.ResultDTO;
import com.alan.community.dto.dbCommentDTO;
import com.alan.community.enums.CommentTypeEnum;
import com.alan.community.exception.CustomizeErrorCode;
import com.alan.community.model.Comment;
import com.alan.community.model.User;
import com.alan.community.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author alan
 * @date 2020/3/22 9:43
 */
@Controller
public class CommentController {

   @Autowired
   private CommentService commentService;

    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    @ResponseBody
    public Object post(@RequestBody CommentDTO commentDTO, HttpServletRequest request){
        User currentUser = (User) request.getSession().getAttribute("CurrentUser");
        if (currentUser==null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (commentDTO==null|| StringUtils.isEmptyOrWhitespace(commentDTO.getContent())) {
             return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO,comment);
        comment.setCommentator(currentUser.getId());
        commentService.addComment(comment,currentUser);
        return ResultDTO.successOf();
    }
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    @ResponseBody
    public  ResultDTO<List<dbCommentDTO>> comments(@PathVariable("id") Long id){
        List<dbCommentDTO> commentDTOS = commentService.queryAllByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.successOf(commentDTOS);
    }
    @RequestMapping("/comment/like")
    @ResponseBody
    public ResultDTO like(@RequestBody HashMap<String,Long> map){
        Long id = map.get("id");
        commentService.incrLike(id);
        return ResultDTO.successOf();
    }
}
