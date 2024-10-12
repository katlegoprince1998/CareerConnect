package com.code.weirdo.CareerConnect.service.auth.register;

import com.code.weirdo.CareerConnect.models.UserCC;
import com.code.weirdo.CareerConnect.response.AuthResponse;

public interface CreateAccount {
    AuthResponse createUser(UserCC user)  throws Exception ;
}
