package com.alan.community.controller;

import com.alan.community.mapper.QuestionMapper;
import com.alan.community.mapper.UserMapper;
import com.alan.community.model.Question;
import com.alan.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author alan
 * @date 2020/3/18 9:53
 */
@Controller
public class PublishController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
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



        User user = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                user = userMapper.selectByToken(token);
                if (user != null)
                    request.getSession().setAttribute("CurrentUser", user);
                break;
            }
        }
        if (user == null) {
            model.addAttribute("msg", "你还没有登陆！");
            return "publish";
        }

        question.setCreatorId(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.addQuestion(question);

        return "redirect:/";

    }
}
