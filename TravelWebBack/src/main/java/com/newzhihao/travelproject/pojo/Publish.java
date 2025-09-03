package com.newzhihao.travelproject.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Publish {
    private Integer contentId;
    private String authorAccount;
    private String title;
    private MultipartFile[] images;
    private String[] imageUrl;
    private LocalDateTime publishTime;
    private Integer readCount;
    private String[] category;
    private String summary;
    private String content;

}
