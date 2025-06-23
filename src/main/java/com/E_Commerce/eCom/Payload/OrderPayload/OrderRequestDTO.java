package com.E_Commerce.eCom.Payload.OrderPayload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    private Long addressId;
    private String paymentMethod;

    private String pgPaymentId;
    private String pgPaymentStatus;
    private String pgResponseMessage;
    private String pgName;
}

