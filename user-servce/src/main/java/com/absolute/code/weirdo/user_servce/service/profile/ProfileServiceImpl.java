package com.absolute.code.weirdo.user_servce.service.profile;

import com.absolute.code.weirdo.user_servce.exceptions.JwtTokenNotFoundException;
import com.absolute.code.weirdo.user_servce.repository.UserRepository;
import com.absolute.code.weirdo.user_servce.request.UserProfileRequest;
import com.absolute.code.weirdo.user_servce.response.UserProfileResponse;
import com.absolute.code.weirdo.user_servce.securityConfi.jwt.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService{
    private final UserRepository repository;

    @Override
    @Transactional
    public UserProfileResponse findUserByJwt(UserProfileRequest request) throws JwtTokenNotFoundException {
        if (request == null) return null;

        String email;
        try {
            email = JwtProvider.getEmailFromJwtToken(request.getJwtToken());
        } catch (JwtTokenNotFoundException e) {
            throw new JwtTokenNotFoundException("Invalid JWT Token");
        }

        if (email == null) return null;

        return Optional.ofNullable(repository.findByEmail(email))
                .map(user -> UserProfileResponse.builder()
                        .userId(user.getUserId())
                        .email(user.getEmail())
                        .fullName(user.getFullName())
                        .role(user.getRole())
                        .build())
                .orElse(null);
    }
}
