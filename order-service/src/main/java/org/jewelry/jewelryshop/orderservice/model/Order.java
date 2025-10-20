package org.jewelry.jewelryshop.orderservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Сущность заказа. Хранит информацию о пользователе, статусе, сумме и временных метках.
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    /** Уникальный идентификатор заказа */
    @Id
    @GeneratedValue
    private UUID id;

    /** Идентификатор пользователя, оформившего заказ */
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    /** Статус заказа */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    /** Итоговая сумма по заказу */
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    /** Время создания записи */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /** Время последнего обновления записи */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Список позиций заказа.
     */
    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<OrderItem> items = new ArrayList<>();
}
