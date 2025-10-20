package org.jewelry.jewelryshop.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Базовая информация о пользователе из user-service: id и при необходимости имя или email.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    /** Идентификатор пользователя */
    private UUID id;
    /** Логин или email */
    private String username;
}
