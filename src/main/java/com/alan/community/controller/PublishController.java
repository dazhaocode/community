package com.alan.community.controller;

import com.alan.community.model.Question;
import com.alan.community.model.User;
import com.alan.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author alan
 * @date 2020/3/18 9:53
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(Question question, Model model, HttpServletRequest request) {

        if (question.getTitle().equals("")) {
            model.addAttribute("msg","标题不能为空！");
            return "publish";
        }
        if (question.getContent().equals("")) {
            model.addAttribute("msg","内容不能为空！");
            return "publish";
        }if (question.getTag().equals("")) {
            model.addAttribute("msg","标签不能为空！");
            return "publish";
        }
        User user = (User) request.getSession().getAttribute("CurrentUser");
        if (user == null) {
            model.addAttribute("msg", "你还没有登陆！");
            return "publish";
        }
        question.setCreatorId(user.getId());
        questionService.addOrUpdateQuestion(question);

        return "redirect:/";

    }

}
