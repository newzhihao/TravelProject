package com.newzhihao.travelproject.service;

import com.newzhihao.travelproject.mapper.ContentCategoryMapper;
import com.newzhihao.travelproject.mapper.ContentMapper;
import com.newzhihao.travelproject.mapper.ImagesMapper;
import com.newzhihao.travelproject.pojo.Publish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PublishService {
    @Autowired
    private ContentCategoryMapper contentCategoryMapper;

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private ImagesMapper imagesMapper;
    /*
    * @Transactional注解表示这个方法是一个事务操作，如果其中任何一步失败，整个操作会回滚
    * */
    @Transactional
    public void publishContent(Publish publish) {
        // 生成内容id
        Integer contentId = contentMapper.selectMaxContentId() + 1;
        publish.setContentId(contentId);
        publish.setPublishTime(LocalDateTime.now());
        publish.setReadCount(0);
        // 插入内容
        contentMapper.insert_content(publish);
        // 插入分类
        contentCategoryMapper.insert_categories(publish);
        // 插入图片
        imagesMapper.insert_images(publish);
    }

}
