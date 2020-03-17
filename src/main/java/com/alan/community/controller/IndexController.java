package com.alan.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author alan
 * @date 2020/3/17 9:52
 */
@Controller
public class IndexController {
    @GetMapping("/")
    public String toIndex(){
        return "index";
    }
}
