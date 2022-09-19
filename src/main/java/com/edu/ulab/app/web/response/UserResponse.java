package com.edu.ulab.app.web.response;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private long userId;
    private String fullName;
    private String title;
    private int age;
}
