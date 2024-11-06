package com.absolute.code.weirdo.user_servce.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserProfileRequest {
    private String jwtToken;
}
