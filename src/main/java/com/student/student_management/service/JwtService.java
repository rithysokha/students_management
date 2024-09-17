package com.student.student_management.service;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
private final String secret = Dotenv.load().get("JWT_SECRET");
    private final UserService userService;

    public JwtService(UserService userService) {
        this.userService = userService;
    }

    public String generateToken(UserDetails userDetails, String tokenType){
        Map<String, String> claims = new HashMap<>();
        claims.put("type", tokenType);
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(claims)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(tokenType.equals("access") ? 3600 : 3600*24*7)))
                .signWith(generateKey())
                .compact();
    }
    private SecretKey generateKey(){
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(decodedKey);
    }
    public String extractUsername(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getSubject();
    }

    private Claims getClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    public boolean isTokenValid(String jwt, String tokenType) {
        Claims claims = getClaims(jwt);
        return claims.getExpiration().after(Date.from(Instant.now())) && tokenType.equals(claims.get("type"));
    }
}
