package org.jewelry.jewelryshop.authservice.dto;

import lombok.Data;

import java.util.List;

/**
 * DTO, в котором User Service отдаёт данные о пользователе
 */
@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String password;
    private List<String> roles;
}
