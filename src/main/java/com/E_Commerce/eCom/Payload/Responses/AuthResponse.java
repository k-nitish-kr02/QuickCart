package com.E_Commerce.eCom.Payload.Responses;

import com.E_Commerce.eCom.Payload.UserPayload.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseCookie;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private ResponseCookie jwtCookie;
    private UserDTO user;
}
