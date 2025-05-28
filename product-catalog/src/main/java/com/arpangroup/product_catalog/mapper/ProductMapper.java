package com.arpangroup.product_catalog.mapper;

import com.arpangroup.product_catalog.dto.ProductCreateRequest;
import com.arpangroup.product_catalog.entity.Category;
import com.arpangroup.product_catalog.entity.Product;
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
