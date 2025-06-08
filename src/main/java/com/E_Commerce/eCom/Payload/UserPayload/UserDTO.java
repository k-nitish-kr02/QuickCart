package com.E_Commerce.eCom.Payload.UserPayload;

import com.E_Commerce.eCom.Model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long userId;
    private String username;
    private String email;
    private Set<Role> roles;
}
