package com.absolute.code.weirdo.user_servce.controller;

import com.absolute.code.weirdo.user_servce.request.UserProfileRequest;
import com.absolute.code.weirdo.user_servce.response.UserProfileResponse;
import com.absolute.code.weirdo.user_servce.service.profile.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/profile/")
public class ProfileController {
    private ProfileService service;

    @GetMapping(value = "/user-profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfileResponse> getUser(@RequestHeader("Authorization") UserProfileRequest request) {
        UserProfileResponse userProfileResponse = service.findUserByJwt(request);

        if (userProfileResponse == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userProfileResponse);
    }
}
