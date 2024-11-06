package com.absolute.code.weirdo.create_job_post_service.controller;

import com.absolute.code.weirdo.create_job_post_service.dto.UserDto;
import com.absolute.code.weirdo.create_job_post_service.exceptions.FailedToCreateJobPostException;
import com.absolute.code.weirdo.create_job_post_service.request.JobPostRequest;
import com.absolute.code.weirdo.create_job_post_service.response.JobPostResponse;
import com.absolute.code.weirdo.create_job_post_service.service.JobPostService;
import com.absolute.code.weirdo.create_job_post_service.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/job-post")
@AllArgsConstructor
public class JobPostController {
    private final JobPostService service;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Object> createJobPost(@RequestBody JobPostRequest request,
                                           @RequestHeader(value = "Authorization", required = false) String jwt) {
        try {
            validateJwt(jwt);

            UserDto user = userService.getUserProfile(jwt);
            if (user == null || user.getRole() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authorized");
            }

            JobPostResponse response = service.createJobPost(request, user.getRole(), user.getUserId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (FailedToCreateJobPostException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void validateJwt(String jwt) throws FailedToCreateJobPostException {
        if (jwt == null || jwt.isEmpty()) {
            throw new FailedToCreateJobPostException("Authorization token is missing");
        }
    }
}
