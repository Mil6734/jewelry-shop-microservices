package org.jewelry.jewelryshop.orderservice.client;


import org.jewelry.jewelryshop.orderservice.dto.UserInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(
        name = "user-service",
        url = "${user-service.base-url}"
)
public interface UserClient {
    /**
     * Проверяем существование пользователя и получаем его данные.
     */
    @GetMapping("/users/{id}")
    UserInfoDTO getUser(@PathVariable("id") UUID userId);
}
