package com.alan.community.controller;

import com.alan.community.dto.QuestionDTO;
import com.alan.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author alan
 * @date 2020/3/19 16:41
 */
@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/details/{id}")
    public String doQuestion(@PathVariable("id")Integer id, Model model){
        QuestionDTO questionDTO = questionService.queryById(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
    @GetMapping("/edit/{id}")
    public String editQuestion(@PathVariable("id")Integer id, Model model){
        QuestionDTO questionDTO = questionService.queryById(id);
        model.addAttribute("question",questionDTO);
        return "publish";
    }

}
