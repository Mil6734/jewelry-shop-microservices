package org.jewelry.jewelryshop.userservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title       = "Jewelry Shop",
                version     = "1.0.0",
                description = "Управление пользователями: регистрация, профиль, роли",
                contact     = @Contact(
                        name  = "Команда JewelryShop",
                        email = "support@jewelryshop.com"
                )
        )
)
public class OpenApiConfig {
}
