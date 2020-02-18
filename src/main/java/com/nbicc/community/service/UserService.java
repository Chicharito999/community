package com.nbicc.community.service;

import com.nbicc.community.mapper.UserMapper;
import com.nbicc.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void InsertOrUpate(User user) {
        User u = userMapper.getAccountId(user.getAccountId());
        if (u != null) {
            u.setName(user.getName());
            u.setToken(user.getToken());
            u.setAvatarUrl(user.getAvatarUrl());
            u.setGmtModified(System.currentTimeMillis());
            userMapper.update(u);
        } else {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insertUser(user);
        }

    }


}
