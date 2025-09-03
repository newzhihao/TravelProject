package com.newzhihao.travelproject.controller;

import com.newzhihao.travelproject.pojo.Result;
import com.newzhihao.travelproject.pojo.User;
import com.newzhihao.travelproject.service.AliyunService;
import com.newzhihao.travelproject.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private AliyunService aliyunService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        User loggedInUser = loginService.login(user.getAuthorAccount(), user.getAuthorPassword());

        if (loggedInUser != null) {
            return Result.success(loggedInUser);
        }
        return Result.error("账号或密码错误");
    }

    @PostMapping("/register")
    public Result register(
        @RequestPart("user") User user,
        @RequestPart("file") MultipartFile file
    )
    {
        String originalFilename = file.getOriginalFilename();
        String extension = null;
        if (originalFilename != null) {
            extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        }
        String uniqueFileName = UUID.randomUUID().toString().replace("-", "") + extension;
        String FileName;
        try {
            FileName = aliyunService.upload(file.getBytes(), uniqueFileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        user.setAuthorPhotoUrl(FileName);
        loginService.Register(user);
        return Result.success();
    }

}
