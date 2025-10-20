package org.jewelry.jewelryshop.catalogservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "DTO для создания или обновления продукта")
public class ProductCreateUpdateDTO {

    @Schema(description = "Название продукта", example = "Кольцо из золота", required = true)
    @NotBlank(message = "Название продукта обязательно")
    private String name;

    @Schema(description = "Описание продукта", example = "Изысканное кольцо с бриллиантами")
    private String description;

    @Schema(description = "Цена продукта", example = "24999.99", required = true)
    @NotNull(message = "Цена обязательна")
    private BigDecimal price;

    @Schema(description = "Материал", example = "Золото")
    private String material;
}
