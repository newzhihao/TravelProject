package com.newzhihao.travelproject.mapper;

import com.newzhihao.travelproject.pojo.ContentCategories;
import com.newzhihao.travelproject.pojo.Publish;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ContentCategoryMapper {
    @Results({@Result(column = "relation_id", property = "relationId"),
            @Result(column = "content_id", property = "contentId"),
            @Result(column = "category", property = "category")})
    @Select("select * from content_categories")
    public List<ContentCategories> selectCategory();
    
    @Insert({
            "<script>",
            "insert into content_categories(content_id, category) values",
            "<foreach collection= 'publish.category' item = 'c' separator=','>",
            "(#{publish.contentId}, #{c})",
            "</foreach>",
            "</script>"
    })
    public void insert_categories(@Param("publish") Publish publish);

    @Results({
            @Result(column = "category", property = "category")})
    @Select("select category from content_categories where content_id = #{contentId}")
    public String[] selectCategoriesByContentId(@Param("contentId") int contentId);
}
