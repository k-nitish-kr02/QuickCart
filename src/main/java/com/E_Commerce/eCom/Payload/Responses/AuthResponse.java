package com.E_Commerce.eCom.Payload.Responses;

import com.E_Commerce.eCom.Payload.UserPayload.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String token;
    private UserDTO user;
}
