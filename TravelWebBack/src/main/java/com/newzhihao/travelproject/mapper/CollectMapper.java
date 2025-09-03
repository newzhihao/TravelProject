package com.newzhihao.travelproject.mapper;

import com.newzhihao.travelproject.pojo.Collection;
import com.newzhihao.travelproject.pojo.Contents;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CollectMapper {

    @Insert("insert into collection (author_account, content_id) values (#{authorAccount}, #{contentId})")
    public void insertCollection(Collection collection);

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
            "    contents c\n" +
            "where content_id in (select content_id from collection where author_account = #{authorAccount})")
    public List<Contents> selectCollection(String authorAccount);

    @Delete("delete from collection where content_id = #{contentId}")
    public void deleteCollection(Integer contentId);


}
