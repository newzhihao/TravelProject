package com.newzhihao.travelproject.service.serviceImpl;

import com.newzhihao.travelproject.mapper.ContentMapper;
import com.newzhihao.travelproject.pojo.Contents;
import com.newzhihao.travelproject.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private ContentMapper contentMapper;

    @Override
    public List<Contents> selectAll() {
        return contentMapper.selectAll();
    }

    @Override
    public Contents selectById(Integer contentId) {
        return contentMapper.selectById(contentId);
    }

    @Override
    public List<Contents> getCardListContents(){
        return contentMapper.getCardListContents();
    };

    @Override
    public List<Contents> getPersonalContents(String authorAccount) {
        return contentMapper.getPersonalContents(authorAccount);
    }

    @Override
    public void deleteContent(Integer contentId) {
        contentMapper.deleteContent(contentId);
    }

    @Override
    public List<Contents> getContentsBySearch(String words) {
        return contentMapper.getContentsBySearch(words);
    }

    @Override
    public List<Contents> getContentsByCategory(String category) {
        return contentMapper.getContentsByCategory(category);
    }
}
