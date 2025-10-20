package org.jewelry.jewelryshop.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Информация о товаре из catalog-service: id и текущая цена.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoDTO {
    /** Идентификатор товара */
    private UUID id;
    /** Цена за единицу товара */
    private BigDecimal price;
}
