package org.jewelry.jewelryshop.authservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.jewelry.jewelryshop.authservice.dto.UserResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.access-token-validity}")
    private String accessValidity;
    @Value("${jwt.refresh-token-validity}")
    private String refreshValidity;

    private Key key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateAccessToken(UserResponseDto u) {
        long ttl = parseDuration(accessValidity);
        return buildToken(u, ttl, "access");
    }

    public String generateRefreshToken(UserResponseDto u) {
        long ttl = parseDuration(refreshValidity);
        return buildToken(u, ttl, "refresh");
    }

    private String buildToken(UserResponseDto u, long ttlMillis, String type) {
        return Jwts.builder()
                .setSubject(u.getUsername())
                .claim("roles", u.getRoles())
                .claim("type", type)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ttlMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private long parseDuration(String spec) {
        // простейший парсер: 15m→15*60*1000, 7d→7*24*60*60*1000
        char unit = spec.charAt(spec.length()-1);
        long val = Long.parseLong(spec.substring(0, spec.length()-1));
        return switch (unit) {
            case 'm' -> val * 60_000;
            case 'h' -> val * 3_600_000;
            case 'd' -> val * 86_400_000;
            default -> throw new IllegalArgumentException("Bad duration: " + spec);
        };
    }
}
