package org.jewelry.jewelryshop.catalogservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "DTO для отображения информации о продукте")
public class ProductDTO {

    @Schema(description = "ID продукта", example = "1")
    private Long id;

    @Schema(description = "Название продукта", example = "Кольцо из золота")
    private String name;

    @Schema(description = "Описание продукта", example = "Кольцо ручной работы с рубином")
    private String description;

    @Schema(description = "Цена продукта", example = "14999.50")
    private BigDecimal price;

    @Schema(description = "Материал", example = "Золото")
    private String material;

    @Schema(description = "Дата создания продукта", example = "2025-04-11T14:32:00")
    private LocalDateTime createdAt;

    @Schema(description = "Признак новинки", example = "true")
    private boolean newProduct;

    @Schema(description = "URL изображения", example = "https://example.com/image.jpg")
    private String imageUrl;
}
