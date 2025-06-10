package com.E_Commerce.eCom.Payload;

import com.E_Commerce.eCom.Payload.ProductPayload.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDTO {
    private Long cartItemId;
    private Integer quantity;

    private Double itemDiscount;

    private Double itemPrice;

    private ProductDTO product;
    private CartDTO cart;
}
