package com.E_Commerce.eCom.Payload.OrderPayload;

import com.E_Commerce.eCom.Payload.ProductPayload.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {

    private Long orderItemId;
    private ProductDTO product;

    private Integer quantity;
    private double discount;
    private double orderedProductPrice;
}
