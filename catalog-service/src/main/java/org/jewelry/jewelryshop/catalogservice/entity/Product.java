package org.jewelry.jewelryshop.catalogservice.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000, nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String material;

    @Column(nullable = false)
    private boolean newProduct = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column
    private String imageUrl;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
