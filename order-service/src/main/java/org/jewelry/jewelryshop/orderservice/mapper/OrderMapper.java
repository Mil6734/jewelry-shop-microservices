package org.jewelry.jewelryshop.orderservice.mapper;

import org.jewelry.jewelryshop.orderservice.dto.OrderItemDTO;
import org.jewelry.jewelryshop.orderservice.dto.OrderItemResponseDTO;
import org.jewelry.jewelryshop.orderservice.dto.OrderRequestDTO;
import org.jewelry.jewelryshop.orderservice.dto.OrderResponseDTO;
import org.jewelry.jewelryshop.orderservice.model.Order;
import org.jewelry.jewelryshop.orderservice.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    /** Преобразование запроса в сущность Order */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Order toEntity(OrderRequestDTO dto);

    /** Преобразование сущности в ответное DTO */
    @Mapping(source = "items", target = "items")
    OrderResponseDTO toDto(Order order);

    /** Для списка заказов */
    List<OrderResponseDTO> toDtoList(List<Order> orders);

    /** DTO сущность позиции */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true) // связь проставим вручную в сервисе
    @Mapping(target = "price", ignore = true) // цена рассчитывается в сервисе
    OrderItem toEntity(OrderItemDTO dto);

    /** Сущность позиции - ответное DTO */
    @Mapping(source = "productId", target = "productId")
    OrderItemResponseDTO toDto(OrderItem item);

    /** Map автоматически подхватит List<OrderItemDTO> → List<OrderItem> и обратно */
}
