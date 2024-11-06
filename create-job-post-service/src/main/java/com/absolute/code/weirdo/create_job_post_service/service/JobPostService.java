package com.absolute.code.weirdo.create_job_post_service.service;

import com.absolute.code.weirdo.create_job_post_service.exceptions.FailedToCreateJobPostException;
import com.absolute.code.weirdo.create_job_post_service.request.JobPostRequest;
import com.absolute.code.weirdo.create_job_post_service.response.JobPostResponse;

public interface JobPostService {
    JobPostResponse createJobPost(JobPostRequest request, String userRole, Long userId) throws FailedToCreateJobPostException;
}
