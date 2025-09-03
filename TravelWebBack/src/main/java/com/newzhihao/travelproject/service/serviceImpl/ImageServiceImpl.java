package com.newzhihao.travelproject.service.serviceImpl;

import com.newzhihao.travelproject.mapper.ImagesMapper;
import com.newzhihao.travelproject.pojo.ContentImages;
import com.newzhihao.travelproject.pojo.User;
import com.newzhihao.travelproject.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImagesMapper imagesMapper;
    @Override
    public List<ContentImages> selectImages() {
        return imagesMapper.selectImages();
    }
    @Override
    public List<ContentImages> selectImagesById(Integer contentId) {
        return imagesMapper.selectImagesById(contentId);
    }


    @Override
    public User get_Avatar(String authorAccount) {
        return imagesMapper.getAvatar(authorAccount);
    }
}
