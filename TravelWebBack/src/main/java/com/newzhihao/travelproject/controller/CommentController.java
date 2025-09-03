package com.newzhihao.travelproject.controller;

import com.newzhihao.travelproject.pojo.Comments;
import com.newzhihao.travelproject.pojo.Result;
import com.newzhihao.travelproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/getComments/{contentId}")
    public Result getComments(@PathVariable Integer contentId){
        List<Comments> comments = commentService.selectCommentsByContentId(contentId);
        return Result.success(comments);
    }

    @PostMapping("/insertComment")
    public Result insertComment(@RequestBody Comments comments){
        commentService.insertComment(comments);
        return Result.success();
    }

}
