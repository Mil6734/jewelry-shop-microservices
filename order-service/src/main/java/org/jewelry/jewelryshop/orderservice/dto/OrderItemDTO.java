package org.jewelry.jewelryshop.orderservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

/**
 * DTO для передачи информации о позиции заказа при создании.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {

    /** Идентификатор товара из catalog-service */
    @NotNull(message = "productId не может быть null")
    private UUID productId;

    /** Количество единиц товара */
    @NotNull(message = "quantity не может быть null")
    @Min(value = 1, message = "quantity должно быть >= 1")
    private Integer quantity;
}
