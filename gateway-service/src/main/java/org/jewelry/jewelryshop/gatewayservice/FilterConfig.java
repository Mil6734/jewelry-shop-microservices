package org.jewelry.jewelryshop.gatewayservice;
import org.jewelry.jewelryshop.gatewayservice.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("catalog", r -> r.path("/api/catalog/**")
                        .filters(f -> f.stripPrefix(2))
                        .uri("lb://catalog-service"))

                .route("users", r -> r.path("/api/users/**")
                        .filters(f -> f.stripPrefix(2).filter(jwtAuthFilter))
                        .uri("lb://user-service"))

                .route("auth", r -> r.path("/api/auth/**")
                        .filters(f -> f.stripPrefix(2))
                        .uri("lb://auth-service"))

                .build();
    }
}
