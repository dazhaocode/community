package com.alan.community.controller;

import com.alan.community.cache.TagCache;
import com.alan.community.dto.ResultDTO;
import com.alan.community.exception.CustomizeErrorCode;
import com.alan.community.exception.CustomizeException;
import com.alan.community.model.Question;
import com.alan.community.model.User;
import com.alan.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

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
    public String publish(Model model){

        model.addAttribute("tags", TagCache.get());
        return "publish";
    }
    @PostMapping("/publish")
    @ResponseBody
    public ResultDTO doPublish(Question question, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("CurrentUser");
        if (user == null) {
          return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        String filterInvalid = TagCache.filterInvalid(question.getTag());
        if (!StringUtils.isEmptyOrWhitespace(filterInvalid)) {
            return ResultDTO.errorOf(CustomizeErrorCode.INVALID_TAG);
        }
        question.setCreatorId(user.getId());
        try {
            questionService.addOrUpdateQuestion(question);
        } catch (CustomizeException e) {
         return ResultDTO.errorOf(e.getCode(),e.getMsg());
        }
        return ResultDTO.successOf();
    }

}
