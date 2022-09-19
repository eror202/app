package com.edu.ulab.app.web.request.update;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
public class UpdatedUserBookRequest {
    private UpdatedUserRequest userRequest;
    private List<UpdatedBookRequest> bookRequests;
}
