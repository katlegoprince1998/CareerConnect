package com.code.weirdo.CareerConnect.controller.jobPost;

import com.code.weirdo.CareerConnect.dto.JobPostDto;
import com.code.weirdo.CareerConnect.service.jobPost.JobPostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job-post/")
@AllArgsConstructor
public class JobPostController {
    private final JobPostService service;

    @PostMapping("/create")
    public ResponseEntity<JobPostDto> create(@RequestBody JobPostDto dto){
        try{
            JobPostDto createJobPost = service.createJobPost(dto);
            return new ResponseEntity<>(createJobPost, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
