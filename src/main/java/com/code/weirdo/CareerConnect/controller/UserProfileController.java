package com.code.weirdo.CareerConnect.controller;

import com.code.weirdo.CareerConnect.dto.UserDto;
import com.code.weirdo.CareerConnect.service.userProfile.UserprofileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class UserProfileController {
    private final UserprofileService service;

    @GetMapping("/user-profile")
    public ResponseEntity<UserDto> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {
        try{
            UserDto user = service.getUserByJwt(jwt);
            if (user != null)
                return  new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
