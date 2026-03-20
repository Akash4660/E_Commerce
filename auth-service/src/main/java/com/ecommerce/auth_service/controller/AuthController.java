package com.ecommerce.auth_service.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.auth_service.dto.RegisterRequest;
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

}

