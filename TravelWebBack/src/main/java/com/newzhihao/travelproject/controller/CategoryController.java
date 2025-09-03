package com.newzhihao.travelproject.controller;

import com.newzhihao.travelproject.pojo.Result;
import com.newzhihao.travelproject.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
    @Autowired
    private ContentCategoryService contentCategoryService;
    @GetMapping("/categories")
    public Result selectCategories() {
        return Result.success(contentCategoryService.selectCategory());
    }
    @GetMapping("/categories/{contentId}")
    public Result getCategoriesByContentId(@PathVariable int contentId) {
        return Result.success(contentCategoryService.selectCategoriesByContentId(contentId));
    }
}
