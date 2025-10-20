package org.jewelry.jewelryshop.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * DTO для обновления данных пользователя
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDTO {

    @Size(min = 3, max = 50)
    private String username;

    @Size(min = 6)
    private String password;

    @Email
    private String email;

    private Set<Long> roleIds;
}
