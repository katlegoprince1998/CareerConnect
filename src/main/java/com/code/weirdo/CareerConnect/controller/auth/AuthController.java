package com.code.weirdo.CareerConnect.controller.auth;

import com.code.weirdo.CareerConnect.models.UserCC;
import com.code.weirdo.CareerConnect.request.AuthRequest;
import com.code.weirdo.CareerConnect.response.AuthResponse;
import com.code.weirdo.CareerConnect.service.auth.login.LogIn;
import com.code.weirdo.CareerConnect.service.auth.register.CreateAccount;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth/")
@RestController
@AllArgsConstructor
public class AuthController {
    private final CreateAccount createAccount;
    private final LogIn login;

    @PostMapping("/register")
    public AuthResponse createAccount(@RequestBody UserCC userCC) throws Exception {
        return createAccount.createUser(userCC);
    }
    @PostMapping("/login")
    public AuthResponse logIn(@RequestBody AuthRequest request) throws Exception {
        return login.logIn(request);
    }
}
