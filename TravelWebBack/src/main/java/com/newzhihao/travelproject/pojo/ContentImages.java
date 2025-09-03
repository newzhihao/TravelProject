package com.newzhihao.travelproject.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentImages {
    private Integer imageId;
    private Integer contentId;
    private String imageUrl;
    private String imageCaption;
}