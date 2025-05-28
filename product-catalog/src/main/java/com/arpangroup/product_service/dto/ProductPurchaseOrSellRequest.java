package com.arpangroup.product_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductPurchaseOrSellRequest {
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "productId cannot be null")
    private Long productId;
}
