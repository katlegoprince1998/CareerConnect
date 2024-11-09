package com.absolute.code.weirdo.user_servce.controller;

import com.absolute.code.weirdo.user_servce.request.SignInRequest;
import com.absolute.code.weirdo.user_servce.request.SignUpRequest;
import com.absolute.code.weirdo.user_servce.response.AuthResponse;
import com.absolute.code.weirdo.user_servce.service.auth.signin.SignInService;
import com.absolute.code.weirdo.user_servce.service.auth.signup.SignUpService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@AllArgsConstructor
public class AuthController {
    private final SignUpService signUpService;
    private final SignInService signInService;
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    // User controller to create an account
    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> createAccount(@RequestBody SignUpRequest request){
        try{
            AuthResponse createdAccount = signUpService.createAccount(request);
            LOG.info("User account created successfully: {}", createdAccount);
            return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            LOG.error("Failed to create account, verify if correct details were inserted {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }
    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> logIn(@RequestBody SignInRequest request){
        try{
            AuthResponse login = signInService.logIn(request);
            LOG.info("Login was successfully");
            return new ResponseEntity<>(login, HttpStatus.OK);
        } catch (RuntimeException e) {
           LOG.error("failed to log user in: {}", e.getMessage());
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
