package org.jewelry.jewelryshop.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO для отображения данных пользователя
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private Set<RoleDTO> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
