package com.alan.community.controller;

import com.alan.community.mapper.UserMapper;
import com.alan.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author alan
 * @date 2020/3/17 9:52
 */
@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/")
    public String toIndex(HttpServletRequest request){
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                User user = userMapper.selectByToken(token);
                if (user!=null)
                    request.getSession().setAttribute("CurrentUser",user);
                break;
            }
        }
        return "index";
    }
}
