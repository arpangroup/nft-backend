package com.arpangroup.product_service.dto;

import com.arpangroup.product_service.entity.Category;
import com.arpangroup.product_service.enums.CurrencyType;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductCreateRequest {
    @NotBlank(message = "productName is required")
    @Size(min = 5, message = "productName must be at least 5 characters long")
    private String productName;

    private long categoryId;

    @Min(value = 1, message = "price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "currencyType type is required")
    private CurrencyType currencyType;

    private String description;

    @JsonSetter("currencyType")
    public void setTransactionType(String type) {
        this.currencyType = CurrencyType.valueOf(type.toUpperCase());
    }
}
