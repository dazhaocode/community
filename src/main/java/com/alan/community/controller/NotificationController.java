package com.alan.community.controller;

import com.alan.community.dto.NotificationDTO;
import com.alan.community.enums.NotificationTypeEnum;
import com.alan.community.model.User;
import com.alan.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @author alan
 * @date 2020/3/28 11:31
 */
@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;


    @GetMapping("/notification/{id}")
    public String doProfile(HttpServletRequest request, @PathVariable("id") Long id) {

        User currentUser = (User) request.getSession().getAttribute("CurrentUser");
        if (currentUser == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(currentUser, id);

        if (NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType() || NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()) {
            return "redirect:/question/details/" + notificationDTO.getOuterid();
        } else {
            return "redirect:/";
        }
    }
}
