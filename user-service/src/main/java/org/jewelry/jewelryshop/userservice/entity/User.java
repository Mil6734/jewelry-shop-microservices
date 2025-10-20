package org.jewelry.jewelryshop.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Логин */
    @Column(unique = true, nullable = false)
    private String username;

    /** Пароль */
    @Column(nullable = false)
    private String password;

    /** Email */
    @Column(unique = true, nullable = false)
    private String email;

    /** Дата создания */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /** Дата обновления */
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /** Роли пользователя */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
