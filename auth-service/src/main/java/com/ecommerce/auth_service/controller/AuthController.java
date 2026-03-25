package com.ecommerce.auth_service.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.auth_service.dto.AuthResponse;
import com.ecommerce.auth_service.dto.LoginRequest;
import com.ecommerce.auth_service.dto.RefreshTokenDto;
import com.ecommerce.auth_service.dto.RegisterRequest;
import com.ecommerce.auth_service.entity.RefreshToken;
import com.ecommerce.auth_service.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

     @PostMapping("/register")
     public String register(@RequestBody @Valid RegisterRequest request) {
         return authService.register(request);
     }

     @GetMapping("/test")
     public String test() {
         return "Test successful";
     }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
            return authService.login(request);
            
    }


    @PostMapping("/refresh")
    public AuthResponse refreshToken(@RequestBody RefreshTokenDto refreshToken) {
        return authService.refreshToken(refreshToken.getRefreshToken());
    }


    @PostMapping("/logout")
   public ResponseEntity<?>  logout(Authentication auth) {
      
      if (auth == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("User not authenticated");
        }

        String email = auth.getName();
        System.out.println("Logging out user: " + email);
            authService.logout(email);

        return ResponseEntity.ok("Logout successful");

}
    
}

