package com.ecommerce.auth_service.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/user")
    public String userApi() {
    return "User API";
    }

    @GetMapping("/debug")
public Object debug(Authentication auth) {
    return auth.getAuthorities();
}   

}
