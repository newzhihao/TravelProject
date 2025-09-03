package com.newzhihao.travelproject.controller;

import com.newzhihao.travelproject.pojo.Result;
import com.newzhihao.travelproject.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {
    @Autowired
    private ImageService imageService;
    @GetMapping("/images")
    public Result selectImages() {
        return Result.success(imageService.selectImages());
    }
    @GetMapping("/images/{contentId}")
    public Result selectById(@PathVariable Integer contentId) {
        if(contentId == null){
            return Result.error("参数错误");
        }else{
            return Result.success(imageService.selectImagesById(contentId));
        }
    }

    @GetMapping("/images/avatar/{authorAccount}")
    public Result selectAvatar(@PathVariable String authorAccount) {
        return Result.success(imageService.get_Avatar(authorAccount));
    }

}
