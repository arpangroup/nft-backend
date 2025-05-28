package com.arpangroup.nft_common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSoldEvent {
    private Long userId;
    private Long productId;
    private LocalDateTime soldAt;

    public ProductSoldEvent(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
        this.soldAt = LocalDateTime.now();
    }
}
