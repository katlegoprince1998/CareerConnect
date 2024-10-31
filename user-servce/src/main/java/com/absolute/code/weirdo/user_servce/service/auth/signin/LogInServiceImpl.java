package com.absolute.code.weirdo.user_servce.service.auth.signin;

import com.absolute.code.weirdo.user_servce.exceptions.FailedToCreateAccountException;
import com.absolute.code.weirdo.user_servce.exceptions.FailedToLoginException;
import com.absolute.code.weirdo.user_servce.repository.UserRepository;
import com.absolute.code.weirdo.user_servce.request.SignUpRequest;
import com.absolute.code.weirdo.user_servce.response.AuthResponse;
import com.absolute.code.weirdo.user_servce.securityConfi.jwt.JwtProvider;
import com.absolute.code.weirdo.user_servce.service.auth.signup.SignUpService;
import com.absolute.code.weirdo.user_servce.service.userConfig.CustomUserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LogInServiceImpl implements SignUpService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserServiceImpl customUserService;
    @Override
    public AuthResponse createAccount(SignUpRequest request) throws FailedToCreateAccountException {
        try{
           if (request != null){
               Authentication authentication = authenticate(request.getEmail(), request.getPassword());
               SecurityContextHolder.getContext().setAuthentication(authentication);

               String token = JwtProvider.generateToken(authentication);
               return AuthResponse.builder()
                       .status(true)
                       .message("User logged in Successfully")
                       .jwt(token)
                       .build();
           }
        } catch (Exception e) {
            throw new FailedToLoginException("Failed to log user in");
        }
        return null;
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customUserService.loadUserByUsername(email);
        if (userDetails == null)
            throw new BadCredentialsException("User details is null: " + null);

        if (!passwordEncoder.matches(password, userDetails.getPassword()))
            throw new BadCredentialsException("Incorrect password:  " + password);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    }

}
