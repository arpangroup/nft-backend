package com.arpangroup.product_catalog.repository;

import com.arpangroup.product_catalog.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByIdIn(List<Long> ids);
}
