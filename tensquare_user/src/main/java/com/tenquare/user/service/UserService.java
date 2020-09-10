package com.tenquare.user.service;

import com.tenquare.user.dao.UserMapper;
import com.tenquare.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jkun
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User login(User user) {
        return userMapper.selectOne(user);
    }

}
