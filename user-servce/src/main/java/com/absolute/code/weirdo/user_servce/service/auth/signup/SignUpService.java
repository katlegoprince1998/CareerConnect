package com.absolute.code.weirdo.user_servce.service.auth.signup;

import com.absolute.code.weirdo.user_servce.exceptions.FailedToCreateAccountException;
import com.absolute.code.weirdo.user_servce.request.SignUpRequest;
import com.absolute.code.weirdo.user_servce.response.AuthResponse;

public interface SignUpService {
    AuthResponse createAccount(SignUpRequest request) throws FailedToCreateAccountException;
}
