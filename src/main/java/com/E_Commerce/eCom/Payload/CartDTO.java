package com.E_Commerce.eCom.Payload;

import com.E_Commerce.eCom.Payload.ProductPayload.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO {
    private Long cartId;
    private Double totalPrice=0.0;
    private List<ProductDTO> products;
}
