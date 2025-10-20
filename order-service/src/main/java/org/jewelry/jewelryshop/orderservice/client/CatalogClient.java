package org.jewelry.jewelryshop.orderservice.client;

import org.jewelry.jewelryshop.orderservice.dto.ProductInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@FeignClient(
        name = "catalog-service",
        url = "${catalog-service.base-url}"
)
public interface CatalogClient {
    /**
     * Запрашиваем у catalog-service цены для набора ID.
     */
    @PostMapping("/products/batch")
    List<ProductInfoDTO> getProducts(@RequestBody Set<UUID> productIds);
}
