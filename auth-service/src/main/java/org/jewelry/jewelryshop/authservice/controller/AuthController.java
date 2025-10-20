package org.jewelry.jewelryshop.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jewelry.jewelryshop.authservice.dto.LoginRequest;
import org.jewelry.jewelryshop.authservice.dto.RefreshRequest;
import org.jewelry.jewelryshop.authservice.dto.TokenResponse;
import org.jewelry.jewelryshop.authservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        TokenResponse tokens = authService.login(request);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestBody RefreshRequest request) {
        TokenResponse tokens = authService.refresh(request);
        return ResponseEntity.ok(tokens);
    }

    // Тестовый endpoint для проверки JWT
    @GetMapping("/test/secured")
    public ResponseEntity<String> securedTest() {
        String user = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getName();
        return ResponseEntity.ok("Авторизировался '" + user + "'");

}
}
