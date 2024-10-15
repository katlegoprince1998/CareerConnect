package com.code.weirdo.CareerConnect.service.userProfile;

import com.code.weirdo.CareerConnect.dto.UserDto;
import com.code.weirdo.CareerConnect.dtoConvertor.UserConvertor;
import com.code.weirdo.CareerConnect.models.UserCC;
import com.code.weirdo.CareerConnect.repository.UserRepository;
import com.code.weirdo.CareerConnect.securityConfig.jwt.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserprofileServiceImpl implements UserprofileService{
    private final UserRepository repository;
    private final JwtProvider jwtProvider;
    @Override
    public UserDto getUserByJwt(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        if (email == null)
            throw new Exception("Invalid jwt token...");
        UserCC user = repository.findUserByEmail(email);
        if (user == null)
            throw new Exception("User with this email " + email + " could not be found");

        return UserConvertor.convertToDto(user);
    }
}
