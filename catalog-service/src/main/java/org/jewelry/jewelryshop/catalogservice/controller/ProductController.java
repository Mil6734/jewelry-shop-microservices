package org.jewelry.jewelryshop.catalogservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jewelry.jewelryshop.catalogservice.dto.ProductCreateUpdateDTO;
import org.jewelry.jewelryshop.catalogservice.dto.ProductDTO;
import org.jewelry.jewelryshop.catalogservice.dto.ProductListResponse;
import org.jewelry.jewelryshop.catalogservice.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Каталог товаров", description = "Управление товарами ювелирного каталога")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Получить все товары", description = "Возвращает список всех товаров")
    @ApiResponse(responseCode = "200", description = "Успешное получение",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class)))
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(summary = "Получить товар по ID", description = "Возвращает товар по идентификатору")
    @ApiResponse(responseCode = "200", description = "Товар найден",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class)))
    @ApiResponse(responseCode = "404", description = "Товар не найден")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(
            @Parameter(description = "ID товара") @PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(summary = "Создать новый товар", description = "Создаёт новый товар")
    @ApiResponse(responseCode = "201", description = "Товар создан",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class)))
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(
            @Valid @RequestBody ProductCreateUpdateDTO requestDto) {
        ProductDTO created = productService.createProduct(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Обновить товар", description = "Обновляет существующий товар по ID")
    @PutMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Товар обновлён")
    public ResponseEntity<ProductDTO> updateProduct(
            @Parameter(description = "ID товара") @PathVariable Long id,
            @Valid @RequestBody ProductCreateUpdateDTO requestDto) {
        ProductDTO updated = productService.updateProduct(id, requestDto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Удалить товар", description = "Удаляет товар по ID")
    @ApiResponse(responseCode = "204", description = "Товар удалён")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Пагинация товаров", description = "Возвращает страницу с товарами")
    @GetMapping("/page")
    public ResponseEntity<ProductListResponse> getProductsPage(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(productService.getProductsPage(pageable));
    }

    @Operation(summary = "Фильтрация товаров", description = "Фильтрация по цене, материалу, новизне и пагинации")
    @GetMapping("/search")
    public ResponseEntity<ProductListResponse> searchProducts(
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String material,
            @RequestParam(required = false) Boolean newProduct,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        ProductListResponse result = productService.getFilteredProducts(minPrice, maxPrice, material, newProduct, pageable);
        return ResponseEntity.ok(result);
    }
}
