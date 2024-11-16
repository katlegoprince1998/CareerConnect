package com.absolute.code.weirdo.create_job_post_service.controller;

import com.absolute.code.weirdo.create_job_post_service.dto.CompanyDto;
import com.absolute.code.weirdo.create_job_post_service.dto.UserDto;
import com.absolute.code.weirdo.create_job_post_service.exceptions.FailedToCreateJobPostException;
import com.absolute.code.weirdo.create_job_post_service.exceptions.FailedToGetJobPostException;
import com.absolute.code.weirdo.create_job_post_service.request.JobPostRequest;
import com.absolute.code.weirdo.create_job_post_service.request.UpdateJobPostRequest;
import com.absolute.code.weirdo.create_job_post_service.response.CreateJobPostResponse;
import com.absolute.code.weirdo.create_job_post_service.response.GetJobPostResponse;
import com.absolute.code.weirdo.create_job_post_service.service.JobPostService;
import com.absolute.code.weirdo.create_job_post_service.service.company.CompanyService;
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
    private final CompanyService companyService;

    @PostMapping("/{companyId}")
    public ResponseEntity<Object> createJobPost(@RequestBody JobPostRequest request,
                                                @RequestHeader(value = "Authorization"
                                                        , required = false) String jwt,
                                                @PathVariable Long companyId) {
        try {
            validateJwt(jwt);

            UserDto user = userService.getUserProfile(jwt);
            if (user == null || user.getRole() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authorized");
            }

            CompanyDto company = companyService.getCompany(jwt, companyId);

            if (company == null)
                throw new FailedToCreateJobPostException("Company with Id " + companyId + " does not exist");

            CreateJobPostResponse response = service.createJobPost(request, user.getRole(), user.getUserId(), companyId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (FailedToCreateJobPostException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getJobPost(@PathVariable Long id,
                                             @RequestHeader(value = "Authorization", required = false) String jwt){

        try{
            if (jwt == null || jwt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authorized");
            }
            UserDto user = userService.getUserProfile(jwt);
            if (user == null || user.getRole() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authorized");
            }
            GetJobPostResponse response = service.getJobPost(id);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (FailedToGetJobPostException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteJobPost(@PathVariable Long id,
                                                @RequestHeader(value = "Authorization", required = false) String jwt) {
        try {
            validateJwt(jwt);
            UserDto user = userService.getUserProfile(jwt);

            if (!"ADMIN".equals(user.getRole())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only ADMIN can delete job posts");
            }

            service.deleteJobPost(id, user.getRole());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (FailedToGetJobPostException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateJobPost(@PathVariable Long id,
                                                @RequestBody UpdateJobPostRequest request,
                                                @RequestHeader(value = "Authorization", required = false) String jwt) {
        try {
            validateJwt(jwt);
            UserDto user = userService.getUserProfile(jwt);

            if (!"ADMIN".equals(user.getRole())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only ADMIN can update job posts");
            }

            GetJobPostResponse response = service.updateJobPost(id, request, user.getRole());
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (FailedToGetJobPostException e) {
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
