package com.E_Commerce.eCom.Requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;

    private Set<String> roles;
}
