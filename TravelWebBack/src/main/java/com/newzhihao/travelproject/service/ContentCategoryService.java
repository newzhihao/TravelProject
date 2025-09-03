package com.newzhihao.travelproject.service;

import com.newzhihao.travelproject.pojo.ContentCategories;

import java.util.List;

public interface ContentCategoryService {
    public List<ContentCategories> selectCategory();
    public String[] selectCategoriesByContentId(int contentId);
}
