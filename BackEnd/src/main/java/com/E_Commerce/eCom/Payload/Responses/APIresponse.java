package com.E_Commerce.eCom.Payload.Responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIresponse {

    private String message;
    private boolean status;
}
