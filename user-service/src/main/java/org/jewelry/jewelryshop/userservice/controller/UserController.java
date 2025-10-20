package org.jewelry.jewelryshop.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jewelry.jewelryshop.userservice.dto.*;
import org.jewelry.jewelryshop.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "Users", description = "CRUD для пользователей")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Получить список всех пользователей (только admin)")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(authentication, #id)")
    @Operation(summary = "Получить пользователя по ID (admin)")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Зарегистрировать нового пользователя")
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserCreateDTO dto) {
        UserDTO created = userService.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(authentication, #id)")
    @Operation(summary = "Обновить данные пользователя (admin)")
    public ResponseEntity<UserDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(authentication, #id)")
    @Operation(summary = "Удалить пользователя (admin)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    @Operation(summary = "Получить профиль текущего пользователя")
    public ResponseEntity<UserDTO> getCurrent(@AuthenticationPrincipal UserDetails principal) {
        UserDTO dto = userService.getCurrent(principal.getUsername());
        return ResponseEntity.ok(dto);
    }

    /** Обновить свой профиль */
    @PutMapping("/me")
    @Operation(summary = "Обновить профиль текущего пользователя")
    public ResponseEntity<UserDTO> updateCurrent(
            @AuthenticationPrincipal UserDetails principal,
            @Valid @RequestBody UserUpdateDTO dto) {
        UserDTO updated = userService.updateCurrent(principal.getUsername(), dto);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Запрос на сброс пароля по email")
    public ResponseEntity<Map<String, String>> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest req) {
        String token = userService.createPasswordResetToken(req.getEmail());
        // пока возвращаем токен прямо в ответе для теста
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Установить новый пароль по токену")
    public ResponseEntity<Void> resetPassword(
            @Valid @RequestBody ResetPasswordRequest req) {
        userService.resetPassword(req.getToken(), req.getNewPassword());
        return ResponseEntity.noContent().build();
    }

    /**
     * Публичный API для Auth Service
     */
    @GetMapping("/by-username/{username}")
    @Operation(summary = "Получить данные пользователя для аутентификации")
    public ResponseEntity<UserDetailsDTO> getByUsername(
            @PathVariable String username) {
        // getByUsernameDetails бросит ResponseStatusException если не найден
        UserDetailsDTO details = userService.getByUsernameDetails(username);
        return ResponseEntity.ok(details);
    }
}
