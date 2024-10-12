package com.code.weirdo.CareerConnect.service.auth.register;

import com.code.weirdo.CareerConnect.models.UserCC;
import com.code.weirdo.CareerConnect.repository.UserRepository;
import com.code.weirdo.CareerConnect.response.AuthResponse;
import com.code.weirdo.CareerConnect.securityConfig.jwt.JwtProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateAccountImpl implements CreateAccount{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final JwtProvider jwtProvider;

    public CreateAccountImpl(PasswordEncoder passwordEncoder, UserRepository repository, JwtProvider jwtProvider){
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
        this.jwtProvider = jwtProvider;
    }
    @Override
    public AuthResponse createUser(UserCC user) throws Exception {
        String email = user.getEmail();
        String fullName = user.getFullName();;
        String password = user.getPassword();

        UserCC emailExist = repository.findUserByEmail(email);
        if (emailExist != null)
            throw new Exception("User with this " + email + ", please user a different email");

        UserCC createUser = UserCC
                .builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .fullName(fullName)
                .build();

        repository.save(createUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        return AuthResponse
                .builder()
                .jwt(token)
                .message("Account created successfully")
                .build();
    }
}
