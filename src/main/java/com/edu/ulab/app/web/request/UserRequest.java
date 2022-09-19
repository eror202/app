package com.edu.ulab.app.web.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class UserRequest {
    private String fullName;
    private String title;
    private int age;
}
