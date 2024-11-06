package com.absolute.code.weirdo.create_job_post_service.service;

import com.absolute.code.weirdo.create_job_post_service.exceptions.FailedToCreateJobPostException;
import com.absolute.code.weirdo.create_job_post_service.model.JobPost;
import com.absolute.code.weirdo.create_job_post_service.repository.JobPostRepository;
import com.absolute.code.weirdo.create_job_post_service.request.JobPostRequest;
import com.absolute.code.weirdo.create_job_post_service.response.JobPostResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

import org.springframework.util.StringUtils;

@Service
@AllArgsConstructor
public class JobPostServiceImpl implements JobPostService {
    private final JobPostRepository repository;

    @Override
    public JobPostResponse createJobPost(JobPostRequest request, String userRole, Long userId) throws FailedToCreateJobPostException {
        validateRequest(request, userRole);

        JobPost jobPost = buildJobPost(request, userId);

        try {
            JobPost savedJobPost = repository.save(jobPost);
            return buildResponse(savedJobPost);
        } catch (Exception e) {
            throw new FailedToCreateJobPostException("Failed to create job post: " + e.getMessage());
        }
    }

    private void validateRequest(JobPostRequest request, String userRole) throws FailedToCreateJobPostException {
        if (request == null) {
            throw new FailedToCreateJobPostException("Request object is null");
        }

        if (!"ADMIN".equals(userRole)) {
            throw new FailedToCreateJobPostException("User is not authorized to create job posts");
        }

    }


    private JobPost buildJobPost(JobPostRequest request, Long userId) {
        return JobPost.builder()
                .jobDescription(request.getJobDescription())
                .postCreatedAt(LocalDate.now())
                .jobType(request.getJobType())
                .jobTitle(request.getJobTitle())
                .applicationDeadline(request.getApplicationDeadline())
                .salaryRange(request.getSalaryRange())
                .experience(request.getExperience())
                .benefits(request.getBenefits())
                .applicationProcess(request.getApplicationProcess())
                .qualifications(request.getQualifications())
                .userid(userId)
                .build();
    }

    private JobPostResponse buildResponse(JobPost savedJobPost) {
        return JobPostResponse.builder()
                .message("Job Post Created Successfully")
                .jobTitle(savedJobPost.getJobTitle())
                .jobId(savedJobPost.getId())
                .userId(savedJobPost.getUserid())
                .build();
    }
}
