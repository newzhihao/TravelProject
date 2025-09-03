package com.newzhihao.travelproject.service.serviceImpl;

import com.newzhihao.travelproject.mapper.UserMapper;
import com.newzhihao.travelproject.pojo.User;
import com.newzhihao.travelproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectUsers() {
        return userMapper.selectUsers();
    }

    @Override
    public User getUserByAccount(String account) {
        return userMapper.selectUserByAccount(account);
    }

}
