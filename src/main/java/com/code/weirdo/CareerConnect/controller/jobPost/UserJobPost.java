package com.code.weirdo.CareerConnect.controller.jobPost;

import com.code.weirdo.CareerConnect.dto.JobPostDto;
import com.code.weirdo.CareerConnect.service.jobPost.JobPostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class UserJobPost {
    private final JobPostService service;

    @GetMapping("/job-post/{id}")
    public ResponseEntity<JobPostDto> getJobPostById(@PathVariable Long id) {
        try {
            JobPostDto jobPostDto = service.getJobPostById(id);
            return ResponseEntity.ok(jobPostDto); // 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 Not Found
        }
    }
    @GetMapping("/job-posts")
    public ResponseEntity<List<JobPostDto>> getAllJobPosts() {
        try {
            List<JobPostDto> jobPosts = service.getAllJobPosts();
            if (jobPosts.isEmpty()) {
                // Optionally return a 204 No Content if no job posts are found
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(jobPosts); // 200 OK
        } catch (Exception e) {
            // Return a 500 Internal Server Error for unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
