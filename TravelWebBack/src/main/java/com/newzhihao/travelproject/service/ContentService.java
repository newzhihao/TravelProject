package com.newzhihao.travelproject.service;

import com.newzhihao.travelproject.pojo.Contents;

import java.util.List;

public interface ContentService {
    public List<Contents> selectAll();
    public Contents selectById(Integer contentId);
    public List<Contents> getCardListContents();
    List<Contents> getPersonalContents(String authorAccount);
    void deleteContent(Integer contentId);
    public List<Contents> getContentsBySearch(String words);
    public List<Contents> getContentsByCategory(String category);
}