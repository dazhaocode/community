package com.alan.community.service;

import com.alan.community.mapper.UserMapper;
import com.alan.community.model.User;
import com.alan.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author alan
 * @date 2020/3/19 11:54
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User selectByToken(String token){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andTokenEqualTo(token);
        List<User> users = userMapper.selectByExample(userExample);
        return users.get(0);
    }

    public void addOrUpdateUser(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        User dbUser = userMapper.selectByExample(userExample).get(0);
        if (dbUser==null) {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            userMapper.updateByPrimaryKeySelective(dbUser);
        }
    }

}
