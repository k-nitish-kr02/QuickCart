package com.E_Commerce.eCom.Payload.UserPayload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private List<UserDTO> users;
    private Integer pageNumber;
    private Long totalElements;
    private Integer pageSize;
    private Integer totalPages;
    private boolean lastPage;

}
