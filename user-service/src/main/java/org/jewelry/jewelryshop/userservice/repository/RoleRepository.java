package org.jewelry.jewelryshop.userservice.repository;

import org.jewelry.jewelryshop.userservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с ролями
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
