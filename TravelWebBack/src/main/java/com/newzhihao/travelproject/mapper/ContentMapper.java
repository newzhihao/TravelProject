package com.newzhihao.travelproject.mapper;

import com.newzhihao.travelproject.pojo.Contents;
import com.newzhihao.travelproject.pojo.Publish;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ContentMapper {
    @Results({@Result(column = "content_id", property = "contentId"),
            @Result(column = "publish_time", property = "publishTime"),
            @Result(column = "read_count", property = "readCount"),
            @Result(column = "author_account", property = "authorAccount")})
    @Select("select * from contents")
    public List<Contents> selectAll();

    @Results({@Result(column = "content_id", property = "contentId"),
            @Result(column = "publish_time", property = "publishTime"),
            @Result(column = "read_count", property = "readCount"),
            @Result(column = "author_account", property = "authorAccount")})
    @Select("select * from contents where content_id = #{contentId}")
    public Contents selectById(Integer contentId);

    @Insert("insert into contents (content_id,author_account,title,publish_time,read_count,summary,content) " +
            "values (#{contentId},#{authorAccount},#{title},#{publishTime},#{readCount},#{summary},#{content})")
    public void insert_content(Publish publish);

    @Select("select max(content_id) from contents")
    public Integer selectMaxContentId();

    @Results({@Result(column = "content_id", property = "contentId"),
            @Result(column = "publish_time", property = "publishTime"),
            @Result(column = "read_count", property = "readCount"),
            @Result(column = "author_account", property = "authorAccount"),
            @Result(column = "min_image_url", property = "CoverImageUrl"),})
    @Select("SELECT\n" +
            "    c.*,\n" +
            "    -- 使用子查询获取每个 content_id 对应的最小 image_id 的 image_url\n" +
            "    (SELECT image_url\n" +
            "     FROM content_images\n" +
            "     WHERE content_id = c.content_id\n" +
            "     ORDER BY image_id\n" +
            "     LIMIT 1) AS min_image_url\n" +
            "FROM\n" +
            "    contents c;")
    public List<Contents> getCardListContents();


    @Results({@Result(column = "content_id", property = "contentId"),
            @Result(column = "publish_time", property = "publishTime"),
            @Result(column = "read_count", property = "readCount"),
            @Result(column = "author_account", property = "authorAccount"),
            @Result(column = "min_image_url", property = "CoverImageUrl"),})
    @Select("SELECT\n" +
            "    c.*,\n" +
            "    -- 使用子查询获取每个 content_id 对应的最小 image_id 的 image_url\n" +
            "    (SELECT image_url\n" +
            "     FROM content_images\n" +
            "     WHERE content_id = c.content_id\n" +
            "     ORDER BY image_id\n" +
            "     LIMIT 1) AS min_image_url\n" +
            "FROM\n" +
            "    contents c where author_account=#{authorAccount};")
    public List<Contents> getPersonalContents(String authorAccount);

    @Delete("delete from contents where content_id=#{contentId}")
    public void deleteContent(Integer contentId);

    @Results({
            @Result(column = "content_id", property = "contentId"),
            @Result(column = "publish_time", property = "publishTime"),
            @Result(column = "read_count", property = "readCount"),
            @Result(column = "author_account", property = "authorAccount"),
            @Result(column = "min_image_url", property = "coverImageUrl"),  // 注意属性名大小写规范
    })
    @Select("SELECT\n" +
            "    c.*,\n" +
            "    -- 使用子查询获取每个 content_id 对应的最小 image_id 的 image_url\n" +
            "    (SELECT image_url\n" +
            "     FROM content_images\n" +
            "     WHERE content_id = c.content_id\n" +
            "     ORDER BY image_id\n" +
            "     LIMIT 1) AS min_image_url\n" +
            "FROM\n" +
            "    contents c \n" +
            "WHERE c.title LIKE CONCAT('%', #{words}, '%')")  // 使用concat函数拼接模糊查询条件
    public List<Contents> getContentsBySearch(String words);

    @Results({
            @Result(column = "content_id", property = "contentId"),
            @Result(column = "publish_time", property = "publishTime"),
            @Result(column = "read_count", property = "readCount"),
            @Result(column = "author_account", property = "authorAccount"),
            @Result(column = "min_image_url", property = "coverImageUrl"),  // 注意属性名大小写规范
    })
    @Select("SELECT\n" +
            "    c.*,\n" +
            "    -- 使用子查询获取每个 content_id 对应的最小 image_id 的 image_url\n" +
            "    (SELECT image_url\n" +
            "     FROM content_images\n" +
            "     WHERE content_id = c.content_id\n" +
            "     ORDER BY image_id\n" +
            "     LIMIT 1) AS min_image_url\n" +
            "FROM\n" +
            "    contents c\n" +
            "WHERE content_id in (select content_id from content_categories where category = #{category});")
    public List<Contents> getContentsByCategory(String category);


}