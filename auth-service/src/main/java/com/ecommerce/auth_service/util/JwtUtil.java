package com.ecommerce.auth_service.util;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

  private Key getSignKey() {
    return Keys.hmacShaKeyFor(secret.getBytes());
}

    public String generateToken(String email){
        return Jwts.builder().setSubject(email).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() 
        + expiration)).signWith(getSignKey(), SignatureAlgorithm.HS512).compact();
    }

    public String extractEmail(String token){
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody().getSubject();
    }
    
    public boolean validateToken(String token,String email){
        return extractEmail(token).equals(email);
    }
}
