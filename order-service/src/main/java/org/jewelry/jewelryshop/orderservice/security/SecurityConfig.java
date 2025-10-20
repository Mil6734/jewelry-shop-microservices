package org.jewelry.jewelryshop.orderservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Настройка Spring Security:JWT-фильтр и правила доступа
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // отключаем CSRF
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // настраиваем правила доступа
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,   "/orders").hasRole("USER")
                        .requestMatchers(HttpMethod.GET,    "/orders/user", "/orders/*").hasRole("USER")
                        .requestMatchers(HttpMethod.GET,    "/orders").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH,  "/orders/*/status").hasRole("ADMIN")
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                        .anyRequest().authenticated()
                )
                // вставляем JWT‐фильтр до стандартного UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
