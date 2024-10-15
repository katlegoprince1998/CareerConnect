package com.code.weirdo.CareerConnect.securityConfig.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtProvider {
    private final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.JWT_SECRETE.getBytes(StandardCharsets.UTF_8));
    public String generateToken(Authentication authentication){
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000))
                .claim("email", authentication.getName())
                .signWith(key).compact();
    }
    public String getEmailFromJwtToken(String jwt){
        jwt = jwt.substring(7);
        Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

        return String.valueOf(claims.get("email"));
    }
}