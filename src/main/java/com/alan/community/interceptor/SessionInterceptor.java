package com.alan.community.interceptor;

import com.alan.community.model.User;
import com.alan.community.service.NotificationService;
import com.alan.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author alan
 * @date 2020/3/19 12:14
 */
@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Value("${gitee.client.redirect}")
    private String redirectUri;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //设置 context 级别的属性
        request.getServletContext().setAttribute("redirectUri", redirectUri);
             Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length != 0) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        String token = cookie.getValue();
                        User user = userService.selectByToken(token);
                        if (user != null) {
                            request.getSession().setAttribute("CurrentUser", user);
                            Long unreadCount = notificationService.unreadCount(user.getId());
                            request.getSession().setAttribute("unreadCount",unreadCount);
                        }
                        break;
                    }
                }
            }
        return true;
    }

}
