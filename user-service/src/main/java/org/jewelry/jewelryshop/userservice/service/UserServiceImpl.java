package org.jewelry.jewelryshop.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jewelry.jewelryshop.userservice.dto.UserCreateDTO;
import org.jewelry.jewelryshop.userservice.dto.UserDTO;
import org.jewelry.jewelryshop.userservice.dto.UserDetailsDTO;
import org.jewelry.jewelryshop.userservice.dto.UserUpdateDTO;
import org.jewelry.jewelryshop.userservice.entity.PasswordResetToken;
import org.jewelry.jewelryshop.userservice.entity.Role;
import org.jewelry.jewelryshop.userservice.entity.User;
import org.jewelry.jewelryshop.userservice.mapper.UserMapper;
import org.jewelry.jewelryshop.userservice.repository.PasswordResetTokenRepository;
import org.jewelry.jewelryshop.userservice.repository.RoleRepository;
import org.jewelry.jewelryshop.userservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository tokenRepository;

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Пользователь не найден: " + id));
        return userMapper.toDto(user);
    }

    @Override
    public UserDTO create(UserCreateDTO dto) {
        // DTO → Entity
        User user = userMapper.toEntity(dto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // подгружаем роли из базы
        Set<Role> roles = roleRepository.findAllById(dto.getRoleIds() != null ? dto.getRoleIds() : Set.of())
                .stream()
                .collect(Collectors.toSet());
        user.setRoles(roles);

        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    @Override
    public UserDTO update(Long id, UserUpdateDTO dto) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Пользователь не найден: " + id));

        if (dto.getPassword() != null) {
            existing.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        userMapper.updateEntityFromDto(dto, existing);

        if (dto.getRoleIds() != null) {
            Set<Role> roles = roleRepository.findAllById(dto.getRoleIds())
                    .stream().collect(Collectors.toSet());
            existing.setRoles(roles);
        }

        User saved = userRepository.save(existing);
        return userMapper.toDto(saved);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO getCurrent(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Пользователь не найден: " + username));
        return userMapper.toDto(user);
    }

    @Override
    public UserDTO updateCurrent(String username, UserUpdateDTO dto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Пользователь не найден: " + username));

        if (dto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        userMapper.updateEntityFromDto(dto, user);

        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    @Override
    public String createPasswordResetToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Электронная почта не найдена " + email));

        String token = UUID.randomUUID().toString();
        PasswordResetToken prt = PasswordResetToken.builder()
                .user(user)
                .token(token)
                .expiryDate(LocalDateTime.now().plusHours(1))
                .build();
        tokenRepository.save(prt);

        log.info("Токен сброса пароля для {}: {}", email, token);
        return token;
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken prt = tokenRepository.findByToken(token)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Недействительный токен"));

        if (prt.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenRepository.deleteByToken(token);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Срок действия токена истек");
        }

        User user = prt.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepository.delete(prt);
    }

    @Override
    public UserDetailsDTO getByUsernameDetails(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Пользователь не найден: " + username));

        Set<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return UserDetailsDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }
}
