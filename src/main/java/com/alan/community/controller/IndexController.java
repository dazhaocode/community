package com.alan.community.controller;

import com.alan.community.dto.QuestionDTO;
import com.alan.community.mapper.QuestionMapper;
import com.alan.community.mapper.UserMapper;
import com.alan.community.model.Question;
import com.alan.community.model.User;
import com.alan.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author alan
 * @date 2020/3/17 9:52
 */
@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping({"/","/index"})
    public String toIndex(HttpServletRequest request, Model model){
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                User user = userMapper.selectByToken(token);
                if (user!=null)
                    request.getSession().setAttribute("CurrentUser", user);
                break;
            }
        }

        List<QuestionDTO> questionList=questionService.queryAllQuestion();
        model.addAttribute("questions",questionList);
        return "index";
    }
}
