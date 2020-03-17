package com.alan.community.controller;

import com.alan.community.dto.AccessTokenDTO;
import com.alan.community.dto.GiteeUser;
import com.alan.community.provider.GiteeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author alan
 * @date 2020/3/17 11:05
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GiteeProvider giteeProvider;

    @Value("${gitee.client.id}")
    private String client_id;

    @Value("${gitee.client.secret}")
    private  String client_secret;

    @Value("${gitee.client.redirect}")
    private  String redirect_uri;

    @Value("${gitee.client.Grant_type}")
    private  String Grant_type;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code){


        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setGrant_type(Grant_type);
        String accessToken = giteeProvider.getAccessToken(accessTokenDTO);
        GiteeUser user = giteeProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";

    }
}
