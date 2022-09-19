package com.edu.ulab.app.web.response;

import lombok.*;

import java.util.List;

@Data
@Builder
public class UserBookResponse {
    private Long userId;
    private List<Long> booksIdList;
}
