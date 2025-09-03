package com.newzhihao.travelproject.service;

import com.newzhihao.travelproject.pojo.User;

import java.util.List;

public interface UserService {
    public List<User> selectUsers();
    User getUserByAccount(String account);

}
