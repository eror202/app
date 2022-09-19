package com.edu.ulab.app.web.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
public class UserBookRequest {
    private UserRequest userRequest;
    private List<BookRequest> bookRequests;
}
