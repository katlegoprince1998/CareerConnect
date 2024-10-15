package com.code.weirdo.CareerConnect.service.userProfile;
import com.code.weirdo.CareerConnect.dto.UserDto;

public interface UserprofileService {
    UserDto getUserByJwt(String jwt) throws Exception;
}
