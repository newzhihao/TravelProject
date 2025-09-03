package com.newzhihao.travelproject.mapper;

import com.newzhihao.travelproject.pojo.ContentImages;
import com.newzhihao.travelproject.pojo.Publish;
import com.newzhihao.travelproject.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ImagesMapper {
    @Results({@Result(property = "imageId", column = "image_id"),
            @Result(property = "contentId", column = "content_id"),
            @Result(property = "imageUrl", column = "image_url"),
            @Result(property = "imageCaption", column = "image_caption")})
    @Select("select * from content_images")
    public List<ContentImages> selectImages();
    @Results({@Result(property = "imageId", column = "image_id"),
            @Result(property = "contentId", column = "content_id"),
            @Result(property = "imageUrl", column = "image_url"),
            @Result(property = "imageCaption", column = "image_caption")})
    @Select("select * from content_images where content_id=#{contentId}")
    public List<ContentImages> selectImagesById(Integer contentId);

    @Insert({
            "<script>",
            "insert into content_images (content_id, image_url) values ",
            "<foreach collection='publish.imageUrl' item='url' separator=','>",
            "(#{publish.contentId}, #{url})",
            "</foreach>",
            "</script>"
    })
    public void insert_images(@Param("publish") Publish publish);

    @Results({
            @Result(column = "author_account", property = "authorAccount"),
            @Result(column = "author_name", property = "authorName"),
            @Result(column = "author_photo_url", property = "authorPhotoUrl")
    })
    @Select("select author_account,author_name,author_photo_url from users where author_account=#{authorAccount}")
    public User getAvatar(String authorAccount);
}
