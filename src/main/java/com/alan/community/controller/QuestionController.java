package com.alan.community.controller;

import com.alan.community.cache.TagCache;
import com.alan.community.dto.QuestionDTO;
import com.alan.community.dto.dbCommentDTO;
import com.alan.community.enums.CommentTypeEnum;
import com.alan.community.service.CommentService;
import com.alan.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author alan
 * @date 2020/3/19 16:41
 */
@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/details/{id}")
    public String doQuestion(@PathVariable("id") Long id, Model model){
        QuestionDTO questionDTO = questionService.queryById(id);
        questionService.incrViewOrCommentOrLikeCount(id);
        List<QuestionDTO> relatedQuestion = questionService.selectRelated(questionDTO);
        List<dbCommentDTO> dbCommentDTOS = commentService.queryAllByTargetId(id,CommentTypeEnum.QUESTION);
        model.addAttribute("question",questionDTO);
        model.addAttribute("dbCommentDTOS",dbCommentDTOS);
        model.addAttribute("relatedQuestion",relatedQuestion);
        return "question";
    }
    @GetMapping("/edit/{id}")
    public String editQuestion(@PathVariable("id") Long id, Model model){
        QuestionDTO questionDTO = questionService.queryById(id);
        model.addAttribute("tags", TagCache.get());
        model.addAttribute("question",questionDTO);
        return "publish";
    }

}
