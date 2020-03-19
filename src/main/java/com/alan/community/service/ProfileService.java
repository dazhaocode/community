package com.alan.community.service;

import com.alan.community.mapper.QuestionMapper;
import com.alan.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author alan
 * @date 2020/3/19 10:17
 */
@Service
public class ProfileService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

}
