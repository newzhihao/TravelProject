package com.newzhihao.travelproject.mapper;

import com.newzhihao.travelproject.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Results({
            @Result(column = "author_account", property = "authorAccount"),
            @Result(column = "author_name", property = "authorName"),
            @Result(column = "author_password", property = "authorPassword"),
            @Result(column = "author_photo_url", property = "authorPhotoUrl")
    })
    @Select("select * from users")
    public List<User> selectUsers();
    @Results({
            @Result(column = "author_account", property = "authorAccount"),
            @Result(column = "author_name", property = "authorName"),
            @Result(column = "author_password", property = "authorPassword"),
            @Result(column = "author_photo_url", property = "authorPhotoUrl")
    })
    @Select("select * from users where author_account = #{authorAccount}")
    public User selectUserByAccount(String authorAccount);


}
