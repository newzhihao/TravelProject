package com.newzhihao.travelproject.mapper;

import com.newzhihao.travelproject.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginMapper {


    @Results({
            @Result(column = "author_account", property = "authorAccount"),
            @Result(column = "author_name", property = "authorName"),
            @Result(column = "author_password", property = "authorPassword"),
            @Result(column = "author_photo_url", property = "authorPhotoUrl")
    })
    @Select("select * from users where author_account = #{authorAccount}")
    User findByAccount(String authorAccount);


    @Insert("insert into users (author_account, author_name, author_password, author_photo_url) " +
            "values (#{authorAccount}, #{authorName}, #{authorPassword}, #{authorPhotoUrl})")
    public void Register(User user);

}
