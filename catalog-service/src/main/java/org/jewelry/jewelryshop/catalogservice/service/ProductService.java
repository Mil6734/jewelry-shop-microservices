package org.jewelry.jewelryshop.catalogservice.service;

import org.jewelry.jewelryshop.catalogservice.dto.ProductCreateUpdateDTO;
import org.jewelry.jewelryshop.catalogservice.dto.ProductDTO;
import org.jewelry.jewelryshop.catalogservice.dto.ProductListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    // Получить список всех продуктов
    List<ProductDTO> getAllProducts();

    // Получить продукт по ID
    ProductDTO getProductById(Long id);

    // Создать новый продукт
    ProductDTO createProduct(ProductCreateUpdateDTO requestDto);

    // Обновить существующий продукт
    ProductDTO updateProduct(Long id, ProductCreateUpdateDTO requestDto);

    // Удалить продукт по ID
    void deleteProductById(Long id);

    // Метод с пагинацией
    ProductListResponse getProductsPage(Pageable pageable);

    ProductListResponse getFilteredProducts(BigDecimal minPrice,
                                          BigDecimal maxPrice,
                                          String material,
                                          Boolean newProduct,
                                          Pageable pageable);
}
