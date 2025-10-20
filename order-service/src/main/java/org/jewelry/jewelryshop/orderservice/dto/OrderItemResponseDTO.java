package org.jewelry.jewelryshop.orderservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO для возврата информации о позиции заказа.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDTO {
    private UUID productId;
    private Integer quantity;
    private BigDecimal price;
}
