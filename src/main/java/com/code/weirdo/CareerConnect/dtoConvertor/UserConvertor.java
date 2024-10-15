package com.code.weirdo.CareerConnect.dtoConvertor;

import com.code.weirdo.CareerConnect.dto.UserDto;
import com.code.weirdo.CareerConnect.models.UserCC;

public class UserConvertor {
    private UserConvertor(){}

    public static UserDto convertToDto(UserCC user){
        return UserDto
                .builder()
                .email(user.getEmail())
                .fullName(user.getFullName())
                .build();
    }

    public static UserCC convertToEntity(UserDto dto){
        return UserCC
                .builder()
                .email(dto.getEmail())
                .fullName(dto.getFullName())
                .build();
    }
}
