package com.ecommerce.auth_service.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ecommerce.auth_service.entity.RefreshToken;
import com.ecommerce.auth_service.entity.User;
import com.ecommerce.auth_service.repository.RefreshTokenRepository;
import com.ecommerce.auth_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final long refreshTokenDurationMs = 7 * 24 * 60 * 60 * 1000; // 7 days
    
    public RefreshToken createRefreshToken(String email){
        User user = userRepository.findByEmail(email).orElseThrow();
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .build();
        return refreshTokenRepository.save(refreshToken);

    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired");
        }
        return token;
    }
    
}
