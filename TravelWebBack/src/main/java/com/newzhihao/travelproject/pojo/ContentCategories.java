package com.newzhihao.travelproject.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentCategories {
    private Integer relationId;
    private Integer contentId;
    private String category;
}