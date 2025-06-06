package com.E_Commerce.eCom.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 10 , max = 10 , message = "Phone no. must be of 10 digits.")
    private String phoneNumber;
    @NotBlank
    private String street;
    @NotBlank
    private String building;
    @NotBlank
    @Size(min = 2, message = "state name should have atleast two alphabets..")
    private String state;
    @NotBlank
    private String country;
    @NotBlank
    @Size(min = 6)
    private String pincode;

}
