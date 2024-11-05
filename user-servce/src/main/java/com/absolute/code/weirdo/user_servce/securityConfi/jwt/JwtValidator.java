package com.absolute.code.weirdo.user_servce.securityConfi.jwt;

import com.absolute.code.weirdo.user_servce.exceptions.JwtTokenNotFoundException;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class JwtValidator extends OncePerRequestFilter {
    private static final List<String> PUBLIC_PATHS = List.of("/auth/sign-up", "/auth/sign-in");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Skip JWT validation for public endpoints
        String path = request.getRequestURI();
        if (PUBLIC_PATHS.stream().anyMatch(path::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }


        String jwt = request.getHeader(JwtConstant.JWT_HEADER);


        if (jwt == null || !jwt.startsWith("Bearer ")) {

            throw new JwtTokenNotFoundException("JWT Token not found");
        }

        jwt = jwt.substring(7); // Remove "Bearer " prefix

        try {
            SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRETE_KEY.getBytes(StandardCharsets.UTF_8));

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            String email = String.valueOf(claims.get("email"));
            String authorities = String.valueOf(claims.get("authorities"));
            List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
            Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, grantedAuthorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            throw new BadCredentialsException("Invalid JWT token", e);
        }

        filterChain.doFilter(request, response);
    }
}
