package org.jewelry.jewelryshop.userservice.mapper;

import org.jewelry.jewelryshop.userservice.dto.UserCreateDTO;
import org.jewelry.jewelryshop.userservice.dto.UserDTO;
import org.jewelry.jewelryshop.userservice.dto.UserUpdateDTO;
import org.jewelry.jewelryshop.userservice.entity.Role;
import org.jewelry.jewelryshop.userservice.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Маппер между User и соответствующими DTO
 */
@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Entity -> DTO
    @Mapping(source = "roles", target = "roles")
    UserDTO toDto(User entity);

    //DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "roleIds", target = "roles", qualifiedByName = "mapRoleIdsToRoles")
    User toEntity(UserCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "roleIds", target = "roles", qualifiedByName = "mapRoleIdsToRoles")
    void updateEntityFromDto(UserUpdateDTO dto, @MappingTarget User entity);

    // id ролей в Set<Role>
    @Named("mapRoleIdsToRoles")
    default Set<Role> mapRoleIdsToRoles(Set<Long> ids){
        if(ids == null) return null;
        return ids.stream()
                .map(id -> {
                    Role r = new Role();
                    r.setId(id);
                    return r;
                })
                .collect(Collectors.toSet());
    }
}
