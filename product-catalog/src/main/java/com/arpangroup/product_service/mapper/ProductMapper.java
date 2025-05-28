package com.arpangroup.product_service.mapper;

import com.arpangroup.product_service.dto.ProductCreateRequest;
import com.arpangroup.product_service.entity.Category;
import com.arpangroup.product_service.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product mapFrom(ProductCreateRequest request, Category category) {
        Product product = new Product();
        product.setCategory(category);
        product.setName(request.getProductName());
        product.setName(request.getProductName());
        return product;
    }

}
