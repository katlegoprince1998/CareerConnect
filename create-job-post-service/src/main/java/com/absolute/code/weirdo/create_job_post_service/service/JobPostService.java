package com.absolute.code.weirdo.create_job_post_service.service;

import com.absolute.code.weirdo.create_job_post_service.exceptions.FailedToCreateJobPostException;
import com.absolute.code.weirdo.create_job_post_service.exceptions.FailedToGetJobPostException;
import com.absolute.code.weirdo.create_job_post_service.request.JobPostRequest;
import com.absolute.code.weirdo.create_job_post_service.request.UpdateJobPostRequest;
import com.absolute.code.weirdo.create_job_post_service.response.CreateJobPostResponse;
import com.absolute.code.weirdo.create_job_post_service.response.GetJobPostResponse;

import java.util.List;

public interface JobPostService {
    CreateJobPostResponse createJobPost(JobPostRequest request, String userRole, Long userId, Long companyId) throws FailedToCreateJobPostException;
    GetJobPostResponse getJobPost(Long id) throws FailedToGetJobPostException;
    List<GetJobPostResponse> getAllJobPosts() throws FailedToGetJobPostException;
    void deleteJobPost(Long id, String userRole) throws FailedToGetJobPostException;
    GetJobPostResponse updateJobPost(Long id, UpdateJobPostRequest request, String userRole) throws FailedToGetJobPostException;

}
