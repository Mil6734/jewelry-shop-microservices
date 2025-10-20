package org.jewelry.jewelryshop.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Модель для Auth Service
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailsDTO {
    private String username;
    private String password;
    private Set<String> roles;
}
