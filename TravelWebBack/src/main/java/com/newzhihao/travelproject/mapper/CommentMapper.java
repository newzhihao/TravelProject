package com.newzhihao.travelproject.mapper;

import com.newzhihao.travelproject.pojo.Comments;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Results({@Result(column = "author_photo_url", property = "authorPhotoUrl"),
            @Result(column = "author_name", property = "authorName"),
            @Result(column = "user_account", property = "userAccount"),
            @Result(column = "comment_text", property = "commentText"),
            @Result(column = "comment_time", property = "commentTime"),
            @Result(column = "comment_id", property = "commentId")})
    @Select("SELECT \n" +
            "    u.author_photo_url,\n" +
            "    u.author_name,\n" +
            "    c.user_account,\n" +
            "    c.comment_text,\n" +
            "    c.comment_time\n" +
            "FROM \n" +
            "    comments c\n" +
            "JOIN \n" +
            "    users u ON c.user_account = u.author_account\n" +
            "WHERE \n" +
            "    c.content_id = #{contentId};")
    List<Comments> selectCommentsByContentId(Integer contentId);

    @Insert("insert into comments (content_id, user_account, comment_text,comment_time) values (#{contentId}, #{userAccount}, #{commentText},#{commentTime})")
    void insertComment(Comments comments);

}
