package com.newzhihao.travelproject.controller;

import com.newzhihao.travelproject.pojo.Result;
import com.newzhihao.travelproject.pojo.User;
import com.newzhihao.travelproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public Result selectUsers() {
        List<User> users = userService.selectUsers();
        return Result.success(users);
    }

    @GetMapping("/users/{account}")
    public Result getUserByAccount(@PathVariable String account) {
        User user = userService.getUserByAccount(account);
        return Result.success(user);
    }
}
