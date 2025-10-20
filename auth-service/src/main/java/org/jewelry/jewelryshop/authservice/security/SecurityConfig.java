package org.jewelry.jewelryshop.authservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
// разрешаем доступ к самим страницам
                                .requestMatchers(HttpMethod.GET,
                                        "/",
                                        "/index.html",
                                        "/refresh.html",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/favicon.ico",
                                        "/css/**","/js/**","/images/**"
                                ).permitAll()

                                // разрешаем REST для аутентификации
                                .requestMatchers("/auth/**").permitAll()

                                // всё остальное — только с токеном
                                .anyRequest().authenticated()
                )
                //JWT-фильтр
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
