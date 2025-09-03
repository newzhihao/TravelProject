package com.newzhihao.travelproject.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String authorAccount;
    @Getter
    private String authorPassword;
    private String authorName;
    private String authorPhotoUrl;
    private String token;
}
