package org.jewelry.jewelryshop.catalogservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ с пагинированным списком продуктов")
public class ProductListResponse {

    @Schema(description = "Список товаров на текущей странице", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ProductDTO> items;

    @Schema(description = "Номер текущей страницы (начиная с 0)", example = "0")
    private int page;

    @Schema(description = "Размер страницы (кол-во элементов на странице)", example = "10")
    private int size;

    @Schema(description = "Общее количество товаров", example = "150")
    private long totalElements;

    @Schema(description = "Общее количество страниц", example = "15")
    private int totalPages;

    @Schema(description = "Является ли текущая страница последней", example = "true")
    private boolean last;
}
