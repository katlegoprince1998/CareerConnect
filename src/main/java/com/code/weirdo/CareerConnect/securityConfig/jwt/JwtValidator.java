package com.code.weirdo.CareerConnect.securityConfig.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JwtValidator extends OncePerRequestFilter {
    private final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.JWT_SECRETE.getBytes(StandardCharsets.UTF_8));
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       String jwt = request.getHeader(JwtConstant.JWT_HEADER);
       if (jwt != null){
           jwt = jwt.substring(7);
           try{
               Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

               String email =  String.valueOf(claims.get("email"));

               Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, null);
               SecurityContextHolder.getContext().setAuthentication(authentication);
           } catch (Exception e) {
               throw new BadCredentialsException("Invalid token.......");
           }
       }
       filterChain.doFilter(request, response);
    }
}
