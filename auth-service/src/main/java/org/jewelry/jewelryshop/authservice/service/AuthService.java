package org.jewelry.jewelryshop.authservice.service;

import org.jewelry.jewelryshop.authservice.client.UserServiceClient;
import org.jewelry.jewelryshop.authservice.dto.LoginRequest;
import org.jewelry.jewelryshop.authservice.dto.RefreshRequest;
import org.jewelry.jewelryshop.authservice.dto.TokenResponse;
import org.jewelry.jewelryshop.authservice.dto.UserResponseDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserServiceClient userClient;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();

    public AuthService(UserServiceClient userClient, JwtService jwtService) {
        this.userClient = userClient;
        this.jwtService = jwtService;
    }

    public TokenResponse login(LoginRequest req) {
        UserResponseDto user = userClient.getByUsername(req.getUsername());
        if (!pwEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        String access = jwtService.generateAccessToken(user);
        String refresh = jwtService.generateRefreshToken(user);
        return new TokenResponse(access, refresh);
    }

    public TokenResponse refresh(RefreshRequest req) {
        var claims = jwtService.validateToken(req.getRefreshToken());
        // можно здесь проверять тип токена или хранить список в БД
        String username = claims.getSubject();
        UserResponseDto user = userClient.getByUsername(username);
        String access  = jwtService.generateAccessToken(user);
        String refresh = jwtService.generateRefreshToken(user);
        return new TokenResponse(access, refresh);
    }
}
