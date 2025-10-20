package org.jewelry.jewelryshop.orderservice.model;

/**
 * Перечисление возможных статусов заказа.
 */
public enum OrderStatus {
    /** Заказ создан, но не оплачен */
    NEW,
    /** Оплата подтверждена */
    PAID,
    /** Заказ передан в доставку */
    SHIPPED,
    /** Заказ доставлен */
    DELIVERED,
    /** Заказ отменён */
    CANCELLED
}
