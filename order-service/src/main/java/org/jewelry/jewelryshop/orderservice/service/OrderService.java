package org.jewelry.jewelryshop.orderservice.service;

import org.jewelry.jewelryshop.orderservice.dto.OrderRequestDTO;
import org.jewelry.jewelryshop.orderservice.dto.OrderResponseDTO;
import org.jewelry.jewelryshop.orderservice.model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Сервис для управления заказами
 */
public interface OrderService {

    /**
     * Создать новый заказ для пользователя
     *
     * @param userId  ID пользователя (из JWT)
     * @param request DTO с позициями заказа
     * @return ID созданного заказа
     */
    UUID createOrder(UUID userId, OrderRequestDTO request);

    /**
     * Получить детали заказа по его ID
     *
     * @param orderId ID заказа
     * @return DTO с полной информацией о заказе
     */
    OrderResponseDTO getOrder(UUID orderId);

    /**
     * Получить страницу заказов пользователя
     *
     * @param userId   ID пользователя
     * @param pageable постраничная навигация
     * @return страница с DTO заказов
     */
    Page<OrderResponseDTO> getOrdersByUser(UUID userId, Pageable pageable);

    /**
     * Получить страницу всех заказов
     *
     * @param pageable постраничная навигация
     * @return страница DTO заказов
     */
    Page<OrderResponseDTO> getAllOrders(Pageable pageable);

    /**
     * Обновить статус существующего заказа
     *
     * @param orderId   ID заказа
     * @param newStatus новый статус
     */
    void updateStatus(UUID orderId, OrderStatus newStatus);
}
