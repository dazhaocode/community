package com.alan.community.controller;

import com.alan.community.dto.PaginationDTO;
import com.alan.community.dto.SearchDTO;
import com.alan.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

/**
 * @author alan
 * @date 2020/3/17 9:52
 */
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;
    @RequestMapping("/")
    public String toIndex(Model model, PaginationDTO pageDTO, SearchDTO searchDTO){

        PaginationDTO paginationDTO = questionService.queryAllQuestion(pageDTO, searchDTO);

        model.addAttribute("paginationDTO",paginationDTO);
        model.addAttribute("search",searchDTO.getSearch());
        return "index";
    }
}
