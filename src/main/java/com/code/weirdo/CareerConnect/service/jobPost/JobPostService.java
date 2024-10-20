package com.code.weirdo.CareerConnect.service.jobPost;

import com.code.weirdo.CareerConnect.dto.CompanyDto;
import com.code.weirdo.CareerConnect.dto.JobPostDto;

import java.util.List;

public interface JobPostService {
    JobPostDto createJobPost(JobPostDto dto, CompanyDto companyDto) throws Exception;
    JobPostDto updateJobPost(JobPostDto dto, Long id) throws Exception;
    JobPostDto getJobPostById(Long id) throws Exception;
    List<JobPostDto> getAllJobPosts();
    void deleteJobPost(Long id) throws Exception;
    List<JobPostDto> filterJobs(String jobTitle, String jobType, String salaryRange);

}
