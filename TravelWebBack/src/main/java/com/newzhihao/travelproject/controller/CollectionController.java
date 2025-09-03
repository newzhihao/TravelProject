package com.newzhihao.travelproject.controller;

import com.newzhihao.travelproject.pojo.Collection;
import com.newzhihao.travelproject.pojo.Result;
import com.newzhihao.travelproject.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @PostMapping("/collect")
    public Result collect(@RequestBody Collection collections){
        collectionService.insertCollection(collections);
        return Result.success();
    }

    @GetMapping("/getCollection/{authorAccount}")
    public Result getCollection(@PathVariable String authorAccount) {
        return Result.success(collectionService.selectCollection(authorAccount));
    }

    @DeleteMapping("/deleteCollection/{contentId}")
    public Result deleteCollection(@PathVariable Integer contentId) {
        collectionService.deleteCollection(contentId);
        return Result.success();
    }
}
