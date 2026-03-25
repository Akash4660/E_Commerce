package com.ecommerce.auth_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ecommerce.auth_service.security.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtFilter jwtFilter;  

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http
    //         .csrf(csrf -> csrf.disable())  // ← disable CSRF
    //         .authorizeHttpRequests(auth -> auth
    //             .requestMatchers("/api/auth/**").permitAll()  // ← allow auth endpoints
    //             .anyRequest().authenticated()
    //         ).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    //     return http.build();
    // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**","/api/auth/refresh","/api/auth/logout").permitAll()
            .requestMatchers("/api/admin/**","/api/test/**").hasRole("ADMIN")
            .requestMatchers("/api/users","/api/users/**").hasRole("USER")
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
