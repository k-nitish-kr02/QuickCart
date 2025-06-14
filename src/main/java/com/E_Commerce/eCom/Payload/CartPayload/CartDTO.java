package com.E_Commerce.eCom.Payload.CartPayload;

import com.E_Commerce.eCom.Payload.ProductPayload.ProductDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO {
    private Long cartId;
    private Double totalPrice=0.0;
    private List<ProductDTO> products;
}
