package org.jewelry.jewelryshop.catalogservice.config;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrometheusConfiguration {

    @Bean
    public PrometheusMeterRegistryCustomizer prometheusMeterRegistryCustomizer() {
        return registry -> registry.config().commonTags("application", "catalog-service");
    }

    @FunctionalInterface
    public interface PrometheusMeterRegistryCustomizer extends java.util.function.Consumer<PrometheusMeterRegistry> {
    }
}
