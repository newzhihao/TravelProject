package com.newzhihao.travelproject.service;

import com.newzhihao.travelproject.mapper.LoginMapper;
import com.newzhihao.travelproject.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class LoginService {
    @Autowired
    private LoginMapper loginMapper;


    public User login(String authorAccount, String authorPassword) {
        User user = loginMapper.findByAccount(authorAccount);
        if (user != null && user.getAuthorPassword().equals(authorPassword)) {
            // 登录成功后生成token并设置
            Map<String, Object> claims = new HashMap<>();
            claims.put("authorAccount", user.getAuthorAccount());
            claims.put("authorName", user.getAuthorName());
            String token = JwtUtils.generateToken(claims);
            user.setToken(token);
            return user;
        }
        return null;
    }

    public void Register(User user) {
        loginMapper.Register(user);
    }
}
