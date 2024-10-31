package com.absolute.code.weirdo.user_servce.service.auth.signin;

import com.absolute.code.weirdo.user_servce.exceptions.FailedToLoginException;
import com.absolute.code.weirdo.user_servce.request.SignInRequest;
import com.absolute.code.weirdo.user_servce.response.AuthResponse;

public interface SignInService {
    AuthResponse logIn(SignInRequest request) throws FailedToLoginException;
}
