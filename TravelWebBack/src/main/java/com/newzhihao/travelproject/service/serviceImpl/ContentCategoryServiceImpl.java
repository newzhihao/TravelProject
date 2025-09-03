package com.newzhihao.travelproject.service.serviceImpl;

import com.newzhihao.travelproject.mapper.ContentCategoryMapper;
import com.newzhihao.travelproject.mapper.ContentMapper;
import com.newzhihao.travelproject.pojo.ContentCategories;
import com.newzhihao.travelproject.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private ContentCategoryMapper contentCategoryMapper;
    @Override
    public List<ContentCategories> selectCategory() {
        return contentCategoryMapper.selectCategory();
    }
    @Override
    public String[] selectCategoriesByContentId(int contentId) {
        return contentCategoryMapper.selectCategoriesByContentId(contentId);
    }
}

