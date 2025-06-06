package com.E_Commerce.eCom.Payload.CategoryPayload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long categoryId;

    @NotBlank(message = "Category name must not be Blank")
    private String categoryName;
}
