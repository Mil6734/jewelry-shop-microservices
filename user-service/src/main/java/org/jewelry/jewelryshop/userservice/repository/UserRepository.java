package org.jewelry.jewelryshop.userservice.repository;

import org.jewelry.jewelryshop.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с пользователями
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Поиск по логину
     */
    Optional<User> findByUsername(String username);

    /**
     * Поиск по email
     */
    Optional<User> findByEmail(String email);
}
