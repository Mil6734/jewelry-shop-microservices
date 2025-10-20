package org.jewelry.jewelryshop.catalogservice.repository;

import org.jewelry.jewelryshop.catalogservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface ProductFilterRepository {
    Page<Product> findWithFilters(BigDecimal minPrice,
                                  BigDecimal maxPrice,
                                  String material,
                                  Boolean newProduct,
                                  Pageable pageable);
}
