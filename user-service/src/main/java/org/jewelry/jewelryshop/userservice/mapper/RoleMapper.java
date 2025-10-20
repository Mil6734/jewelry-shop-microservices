package org.jewelry.jewelryshop.userservice.mapper;

import org.jewelry.jewelryshop.userservice.dto.RoleDTO;
import org.jewelry.jewelryshop.userservice.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Маппер между Role и RoleDTO
 */
@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDTO toDto(Role role);

    Role toEntity(RoleDTO dto);
}
