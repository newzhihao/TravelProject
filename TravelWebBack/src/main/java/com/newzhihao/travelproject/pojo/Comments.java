package com.newzhihao.travelproject.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments {

    private Integer contentId;
    private String authorPhotoUrl;
    private String authorName;
    private String userAccount;
    private String commentText;
    private LocalDateTime commentTime;
}