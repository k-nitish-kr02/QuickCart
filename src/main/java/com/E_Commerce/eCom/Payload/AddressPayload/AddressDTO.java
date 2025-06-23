package com.E_Commerce.eCom.Payload.AddressPayload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private Long id;
    private String name;
    private String phoneNumber;
    private String street;
    private String building;
    private String state;
    private String country;
    private String pincode;
}
