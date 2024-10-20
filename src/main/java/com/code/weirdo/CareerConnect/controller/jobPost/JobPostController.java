package com.code.weirdo.CareerConnect.controller.jobPost;

import com.code.weirdo.CareerConnect.dto.CompanyDto;
import com.code.weirdo.CareerConnect.dto.JobPostDto;
import com.code.weirdo.CareerConnect.service.company.CompanyService;
import com.code.weirdo.CareerConnect.service.jobPost.JobPostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/job-post/")
@AllArgsConstructor
public class JobPostController {
    private final JobPostService service;
    private final CompanyService companyService;

    @PostMapping("/create/{id}")
    public ResponseEntity<JobPostDto> create(@RequestBody JobPostDto dto, @PathVariable Long id) {
        try {
            CompanyDto company = companyService.findById(id);
            JobPostDto createJobPost = service.createJobPost(dto, company);
            return new ResponseEntity<>(createJobPost, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        }
    }

    @PutMapping("/update-job-post/{id}")
    public ResponseEntity<JobPostDto> updateJobPost(@RequestBody JobPostDto dto, @PathVariable Long id) {
        try {
            // Call the service method to update the job post
            JobPostDto updatedJobPostDto = service.updateJobPost(dto, id);
            return ResponseEntity.ok(updatedJobPostDto);
        } catch (Exception e) {
            // Handle exceptions (e.g., job post not found)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete-job-post/{id}")
    public ResponseEntity<String> deleteJobPost(@PathVariable Long id) {
        try {
            // Call the service method to delete the job post
            service.deleteJobPost(id);
            return ResponseEntity.ok("Deleted Job Post"); // 204 No Content
        } catch (Exception e) {
            // Handle exceptions (e.g., job post not found)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }

}

