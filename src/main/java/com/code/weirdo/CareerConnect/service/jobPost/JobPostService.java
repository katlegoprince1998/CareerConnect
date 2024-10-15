package com.code.weirdo.CareerConnect.service.jobPost;

import com.code.weirdo.CareerConnect.dto.JobPostDto;

import java.util.List;

public interface JobPostService {
    JobPostDto createJobPost(JobPostDto dto) throws Exception;
    JobPostDto updateJobPost(JobPostDto dto, Long id) throws Exception;
    JobPostDto getJobPostById(Long id) throws Exception;
    List<JobPostDto> getAllJobPosts();
    String deleteJobPost(Long id) throws Exception;

}
