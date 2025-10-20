package org.jewelry.jewelryshop.orderservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

/**
 * DTO для создания заказа.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {
    /** Список позиций заказа */
    @NotEmpty(message = "Список items не может быть пустым")
    @Valid
    private List<OrderItemDTO> items;
}
