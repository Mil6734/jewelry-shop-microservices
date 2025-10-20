package org.jewelry.jewelryshop.orderservice.repository;


import org.jewelry.jewelryshop.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    /**
     * Найти все заказы, оформленные пользователем
     *
     * @param userId идентификатор пользователя
     * @return список заказов
     */
    List<Order> findByUserId(UUID userId);
}
