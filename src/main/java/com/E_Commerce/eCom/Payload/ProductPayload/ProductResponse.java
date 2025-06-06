package com.E_Commerce.eCom.Payload.ProductPayload;

import lombok.*;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private List<ProductDTO> content;
    private Integer pageNumber;
    private Long totalElements;
    private Integer pageSize;
    private Integer totalPages;
    private boolean lastPage;

}
