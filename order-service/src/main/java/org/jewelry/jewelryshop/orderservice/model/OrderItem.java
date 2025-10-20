package org.jewelry.jewelryshop.orderservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Сущность позиции в заказе. Ссылается на товар и количество.
 */
@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    /** Уникальный идентификатор позиции */
    @Id
    @GeneratedValue
    private UUID id;

    /** Заказ, к которому относится эта позиция */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    /** Идентификатор товара из catalog-service */
    @Column(name = "product_id", nullable = false)
    private UUID productId;

    /** Количество единиц товара в заказе */
    @Column(nullable = false)
    private Integer quantity;

    /** Цена за единицу товара на момент заказа */
    @Column(nullable = false)
    private BigDecimal price;
}
