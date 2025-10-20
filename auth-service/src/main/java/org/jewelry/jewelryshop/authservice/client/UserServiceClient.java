package org.jewelry.jewelryshop.authservice.client;

import org.jewelry.jewelryshop.authservice.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// имя клиента и базовый URL берётся из application.yml
@FeignClient(name = "user-service", url = "${user-service.base-url}")
public interface UserServiceClient {

    /**
     * Получить пользователя по username.
     * User Service должен отдавать JSON UserResponseDto.
     */
    @GetMapping("/users/by-username/{username}")
    UserResponseDto getByUsername(@PathVariable("username") String username);
}
