package com.alan.community.controller;

import com.alan.community.dto.PaginationDTO;
import com.alan.community.model.User;
import com.alan.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author alan
 * @date 2020/3/17 9:52
 */
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;
    @GetMapping({"/","/index"})
    public String toIndex(Model model,PaginationDTO pageDTO){
        PaginationDTO paginationDTO = questionService.queryAllQuestion(pageDTO,null);
        model.addAttribute("paginationDTO",paginationDTO);
        return "index";
    }
}
