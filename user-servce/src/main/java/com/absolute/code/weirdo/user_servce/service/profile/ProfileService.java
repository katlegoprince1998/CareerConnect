package com.absolute.code.weirdo.user_servce.service.profile;

import com.absolute.code.weirdo.user_servce.exceptions.JwtTokenNotFoundException;
import com.absolute.code.weirdo.user_servce.request.UserProfileRequest;
import com.absolute.code.weirdo.user_servce.response.UserProfileResponse;

public interface ProfileService {
    UserProfileResponse findUserByJwt(UserProfileRequest request) throws JwtTokenNotFoundException;
}
