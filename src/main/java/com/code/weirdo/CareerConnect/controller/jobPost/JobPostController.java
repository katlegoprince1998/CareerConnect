package com.code.weirdo.CareerConnect.controller.jobPost;

import com.code.weirdo.CareerConnect.dto.CompanyDto;
import com.code.weirdo.CareerConnect.dto.JobPostDto;
import com.code.weirdo.CareerConnect.dtoConvertor.CompanyConvertor;
import com.code.weirdo.CareerConnect.models.Company;
import com.code.weirdo.CareerConnect.repository.CompanyRepository;
import com.code.weirdo.CareerConnect.service.company.CompanyService;
import com.code.weirdo.CareerConnect.service.jobPost.JobPostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/job-post/")
@AllArgsConstructor
public class JobPostController {
    private final JobPostService service;
    private final CompanyRepository companyRepository;

    @PostMapping("/create/{id}")
    public ResponseEntity<JobPostDto> create(@RequestBody JobPostDto dto, @PathVariable Long id) {
        try {
            Optional<Company> companyOptional = companyRepository.findById(id);

            if (companyOptional.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Handle case where company is not found
            }
            JobPostDto createJobPost = service.createJobPost(dto, companyOptional.get());
            return new ResponseEntity<>(createJobPost, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Return an appropriate HTTP status code
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

