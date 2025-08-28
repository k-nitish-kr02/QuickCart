package com.E_Commerce.eCom.Payload.RolePayload;

import com.E_Commerce.eCom.Model.AppRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    Set<String> roles;
}
