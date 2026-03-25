package com.ecommerce.auth_service.service;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.auth_service.dto.AuthResponse;
import com.ecommerce.auth_service.dto.LoginRequest;
import com.ecommerce.auth_service.dto.RegisterRequest;
import com.ecommerce.auth_service.entity.RefreshToken;
import com.ecommerce.auth_service.entity.User;
import com.ecommerce.auth_service.exception.EmailAlreadyExistsException;
import com.ecommerce.auth_service.exception.InvalidCredentailsException;
import com.ecommerce.auth_service.exception.InvalidRefreshTokenException;
import com.ecommerce.auth_service.exception.UserNotFoundException;
import com.ecommerce.auth_service.repository.RefreshTokenRepository;
import com.ecommerce.auth_service.repository.UserRepository;
import com.ecommerce.auth_service.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;


    public String register(RegisterRequest request){  
        if (userRepository.existsByEmail(request.getEmail())) {
        throw new EmailAlreadyExistsException("Email already exists");
         }
        User user = User.builder()
        .username(request.getUsername())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
        .build();
        userRepository.save(user);
        return "User registered successfully";
    }



    public AuthResponse login(LoginRequest  request){
        User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentailsException("Invalid credentials");
        }
        String accessToken = jwtUtil.generateToken(user.getEmail());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
        return new AuthResponse(accessToken, refreshToken.getToken());
    }


    public AuthResponse refreshToken(String requestToken) {    
    RefreshToken oldToken = refreshTokenRepository.findByToken(requestToken)
            .orElseThrow(() -> new InvalidRefreshTokenException("Invalid refresh token"));

    refreshTokenService.verifyExpiration(oldToken);

    User user = oldToken.getUser();

    // 🔥 DELETE old refresh token (rotation)
    refreshTokenRepository.delete(oldToken);

    // 🔥 CREATE new refresh token
    RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user.getEmail());

    // 🔥 CREATE new access token
    String newAccessToken = jwtUtil.generateToken(user.getEmail());

    return new AuthResponse(newAccessToken, newRefreshToken.getToken());
    }



    public void logout(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        refreshTokenRepository.deleteByUserId(user.getId());
    }
}
