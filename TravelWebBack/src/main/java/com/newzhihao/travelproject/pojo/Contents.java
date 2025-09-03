package com.newzhihao.travelproject.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contents {
    private Integer contentId;
    private String authorAccount;
    private String title;
    private Date publishTime;
    private Integer readCount;
    private String summary;
    private String content;
    private String CoverImageUrl;

}