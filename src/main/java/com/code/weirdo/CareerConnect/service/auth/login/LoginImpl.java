package com.code.weirdo.CareerConnect.service.auth.login;

import com.code.weirdo.CareerConnect.repository.UserRepository;
import com.code.weirdo.CareerConnect.request.AuthRequest;
import com.code.weirdo.CareerConnect.response.AuthResponse;
import com.code.weirdo.CareerConnect.securityConfig.jwt.JwtProvider;
import com.code.weirdo.CareerConnect.service.CustomUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginImpl implements LogIn{
    private final UserRepository repository;
    private final JwtProvider jwtProvider;
    private CustomUserService customUserService;
    private PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse logIn(AuthRequest request) throws Exception {
        String email = request.getEmail();
        String password = request.getPassword();

        Authentication authentication = authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return AuthResponse
                .builder()
                .message("User Successfully Logged in")
                .jwt(token)
                .build();
    }

    private Authentication authenticate(String email, String password) throws Exception {
        UserDetails userDetails = customUserService.loadUserByUsername(email);

        if (userDetails == null)
            throw new UsernameNotFoundException("User not found with this email: " + email);

        if (!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new Exception("Password di not match");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
