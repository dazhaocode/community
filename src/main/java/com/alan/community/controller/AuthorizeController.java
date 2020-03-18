package com.alan.community.controller;

import com.alan.community.dto.AccessTokenDTO;
import com.alan.community.dto.GiteeUser;
import com.alan.community.mapper.UserMapper;
import com.alan.community.model.User;
import com.alan.community.provider.GiteeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author alan
 * @date 2020/3/17 11:05
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GiteeProvider giteeProvider;

    @Autowired
    private UserMapper userMapper;

    @Value("${gitee.client.id}")
    private String client_id;

    @Value("${gitee.client.secret}")
    private  String client_secret;

    @Value("${gitee.client.redirect}")
    private  String redirect_uri;

    @Value("${gitee.client.Grant_type}")
    private  String Grant_type;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setGrant_type(Grant_type);
        String accessToken = giteeProvider.getAccessToken(accessTokenDTO);
        GiteeUser giteeUser = giteeProvider.getUser(accessToken);
        if (giteeUser!=null){
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(giteeUser.getName());
            user.setAccountId(String.valueOf(giteeUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.addUser(user);
            //login success
            request.getSession().setAttribute("CurrentUser",giteeUser);
            return "redirect:/";
        }else {
            //login fail
            return "redirect:/";
        }

    }
}
