package org.jewelry.jewelryshop.catalogservice.mapper;

import org.jewelry.jewelryshop.catalogservice.dto.ProductDTO;
import org.jewelry.jewelryshop.catalogservice.dto.ProductCreateUpdateDTO;
import org.jewelry.jewelryshop.catalogservice.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", imports = LocalDateTime.class)
public interface ProductMapper {

    // Из DTO в сущность Product (создание)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "newProduct", constant = "true")
    @Mapping(target = "imageUrl", ignore = true)
    Product toEntity(ProductCreateUpdateDTO dto);

    // Из сущности в DTO (ответ клиенту)
    ProductDTO toDto(Product product);

    // Список в DTO
    List<ProductDTO> toDtoList(List<Product> products);
}