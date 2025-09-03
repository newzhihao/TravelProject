package com.newzhihao.travelproject.service;

import com.newzhihao.travelproject.mapper.CommentMapper;
import com.newzhihao.travelproject.pojo.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService{
    @Autowired
    private CommentMapper commentMapper;

    public List<Comments> selectCommentsByContentId(Integer contentId){
        return commentMapper.selectCommentsByContentId(contentId);
    }

    public void insertComment(Comments comment){
        comment.setCommentTime(LocalDateTime.now());
        commentMapper.insertComment(comment);
    }


}
