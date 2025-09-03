package com.newzhihao.travelproject.service;

import com.newzhihao.travelproject.pojo.ContentImages;
import com.newzhihao.travelproject.pojo.User;

import java.util.List;

public interface ImageService {
    public List<ContentImages> selectImages();
    public List<ContentImages> selectImagesById(Integer contentId);

    public User get_Avatar(String authorAccount);
}
