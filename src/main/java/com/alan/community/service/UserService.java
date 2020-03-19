package com.alan.community.service;

import com.alan.community.mapper.UserMapper;
import com.alan.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author alan
 * @date 2020/3/19 11:54
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User selectByToken(String token){
        return userMapper.selectByToken(token);
    }

}
