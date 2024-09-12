package com.zb.service.impl;


import com.zb.entity.User;
import com.zb.mapper.UserMapper;
import com.zb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Service
public class UserServiceimpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUser(String uname) {
        User u = userMapper.findByUser(uname);
        return u;
    }

    @Override
    public void register(String uname, String psw) {
        userMapper.add(uname,psw);
    }
}