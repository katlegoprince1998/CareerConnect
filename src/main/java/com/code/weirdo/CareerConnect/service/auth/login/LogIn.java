package com.code.weirdo.CareerConnect.service.auth.login;

import com.code.weirdo.CareerConnect.request.AuthRequest;
import com.code.weirdo.CareerConnect.response.AuthResponse;

public interface LogIn {
     AuthResponse logIn(AuthRequest request) throws Exception;
}
