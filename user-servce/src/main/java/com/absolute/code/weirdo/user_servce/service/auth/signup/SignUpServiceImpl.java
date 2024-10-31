package com.absolute.code.weirdo.user_servce.service.auth.signup;

import com.absolute.code.weirdo.user_servce.exceptions.FailedToCreateAccountException;
import com.absolute.code.weirdo.user_servce.exceptions.UserWithThisEmailException;
import com.absolute.code.weirdo.user_servce.model.User;
import com.absolute.code.weirdo.user_servce.repository.UserRepository;
import com.absolute.code.weirdo.user_servce.request.SignUpRequest;
import com.absolute.code.weirdo.user_servce.response.AuthResponse;
import com.absolute.code.weirdo.user_servce.securityConfi.jwt.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SignUpServiceImpl implements SignUpService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse createAccount(SignUpRequest request) throws FailedToCreateAccountException {
        try{
            if (request != null){
                User isEmailExisting = userRepository.findByEmail(request.getEmail());
                if (isEmailExisting != null)
                    throw new UserWithThisEmailException("User with this email "  + request.getEmail() + " is existing: ");
                User createdUser = User.builder()
                        .email(request.getEmail())
                        .role(request.getRole())
                        .fullName(request.getFullName())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .build();
                userRepository.save(createdUser);

                Authentication authentication = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                String token = JwtProvider.generateToken(authentication);

                return AuthResponse
                        .builder()
                        .jwt(token)
                        .message("User Account Was Created SuccessFully")
                        .status(true)
                        .build();

            }
        } catch (Exception e) {
            throw new FailedToCreateAccountException("Failed to create account");
        }
        return null;
    }
}
