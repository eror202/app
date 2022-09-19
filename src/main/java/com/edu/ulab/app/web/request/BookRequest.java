package com.edu.ulab.app.web.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class BookRequest {
    private String title;
    private String author;
    private long pageCount;
}
