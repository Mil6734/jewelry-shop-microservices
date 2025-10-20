package org.jewelry.jewelryshop.catalogservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jewelry.jewelryshop.catalogservice.dto.ProductCreateUpdateDTO;
import org.jewelry.jewelryshop.catalogservice.dto.ProductDTO;
import org.jewelry.jewelryshop.catalogservice.dto.ProductListResponse;
import org.jewelry.jewelryshop.catalogservice.entity.Product;
import org.jewelry.jewelryshop.catalogservice.exception.NotFoundException;
import org.jewelry.jewelryshop.catalogservice.mapper.ProductMapper;
import org.jewelry.jewelryshop.catalogservice.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    // Получение всех продуктов
    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        log.debug("Найдено {} продуктов", products.size());
        return productMapper.toDtoList(products);
    }

    // Получение продукта по ID
    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Продукт с id = {} не найден", id);
                    return new  NotFoundException("Продукт с id = " + id + " не найден");
                });
        log.debug("Найден продукт: {}", product);
        return productMapper.toDto(product);
    }

    // Создание нового продукта
    @Override
    public ProductDTO createProduct(ProductCreateUpdateDTO requestDto) {
        Product product = productMapper.toEntity(requestDto);
        Product saved = productRepository.save(product);
        log.debug("Создан продукт: {}", saved);
        return productMapper.toDto(saved);
    }

    // Обновление продукта по ID
    @Override
    public ProductDTO updateProduct(Long id, ProductCreateUpdateDTO requestDto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Невозможно обновить. Продукт с id = {} не найден", id);
                    return new  NotFoundException("Продукт с id = " + id + " не найден");
                });

        Product updated = productMapper.toEntity(requestDto);
        updated.setId(existing.getId());

        Product saved = productRepository.save(updated);
        log.debug("Продукт обновлён: {}", saved);
        return productMapper.toDto(saved);
    }

    // Удаление продукта по ID
    @Override
    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            log.warn("Невозможно удалить. Продукт с id = {} не найден", id);
            throw new NotFoundException("Продукт с id = " + id + " не найден");
        }
        productRepository.deleteById(id);
        log.info("Продукт удалён: id = {}", id);
    }

    @Override
    public ProductListResponse getProductsPage(Pageable pageable) {
        Page<Product> page = productRepository.findAll(pageable);
        log.debug("Получена страница продуктов: номер = {}, размер = {}, всего элементов = {}",
                page.getNumber(), page.getSize(), page.getTotalElements());

        return ProductListResponse.builder()
                .items(productMapper.toDtoList(page.getContent()))
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    @Override
    public ProductListResponse getFilteredProducts(BigDecimal minPrice, BigDecimal maxPrice,
                                                String material, Boolean newProduct, Pageable pageable) {
        Page<Product> page = productRepository.findWithFilters(minPrice, maxPrice, material, newProduct, pageable);
        log.debug("Фильтрация продуктов: найдено {} записей", page.getTotalElements());

        return ProductListResponse.builder()
                .items(productMapper.toDtoList(page.getContent()))
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}
