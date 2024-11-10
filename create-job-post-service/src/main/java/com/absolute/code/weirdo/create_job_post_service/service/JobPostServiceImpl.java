package com.absolute.code.weirdo.create_job_post_service.service;

import com.absolute.code.weirdo.create_job_post_service.exceptions.FailedToCreateJobPostException;
import com.absolute.code.weirdo.create_job_post_service.exceptions.FailedToGetJobPostException;
import com.absolute.code.weirdo.create_job_post_service.model.JobPost;
import com.absolute.code.weirdo.create_job_post_service.repository.JobPostRepository;
import com.absolute.code.weirdo.create_job_post_service.request.JobPostRequest;
import com.absolute.code.weirdo.create_job_post_service.request.UpdateJobPostRequest;
import com.absolute.code.weirdo.create_job_post_service.response.CreateJobPostResponse;
import com.absolute.code.weirdo.create_job_post_service.response.GetJobPostResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JobPostServiceImpl implements JobPostService {
    private final JobPostRepository repository;

    @Override
    public CreateJobPostResponse createJobPost(JobPostRequest request, String userRole, Long userId) throws FailedToCreateJobPostException {
        validateRequest(request, userRole);

        JobPost jobPost = buildJobPost(request, userId);

        try {
            JobPost savedJobPost = repository.save(jobPost);
            return buildResponse(savedJobPost);
        } catch (Exception e) {
            throw new FailedToCreateJobPostException("Failed to create job post: " + e.getMessage());
        }
    }

    @Override
    public GetJobPostResponse getJobPost(Long id) throws FailedToGetJobPostException {
        JobPost jobPost = repository.findById(id)
                .orElseThrow(() -> new FailedToGetJobPostException("Failed to get Job by id " + id));

        return convertToResponse(jobPost);
    }

    // Convert JobPost to GetJobPostResponse
    private GetJobPostResponse convertToResponse(JobPost jobPost) {
        return GetJobPostResponse.builder()
                .id(jobPost.getId())
                .jobTitle(jobPost.getJobTitle())
                .jobType(jobPost.getJobType())
                .salaryRange(jobPost.getSalaryRange())
                .jobDescription(jobPost.getJobDescription())
                .qualifications(jobPost.getQualifications())
                .experience(jobPost.getExperience())
                .skillsRequired(jobPost.getSkillsRequired())
                .applicationProcess(jobPost.getApplicationProcess())
                .applicationDeadline(jobPost.getApplicationDeadline())
                .benefits(jobPost.getBenefits())
                .postCreatedAt(jobPost.getPostCreatedAt())
                .userId(jobPost.getUserid()) // Assumes a method to convert user entity to UserDto
                .build();
    }


    @Override
    public List<GetJobPostResponse> getAllJobPosts() throws FailedToGetJobPostException {
        List<JobPost> jobPosts = repository.findAll();
        return jobPosts.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteJobPost(Long id, String userRole) throws FailedToGetJobPostException {
        if (!"ADMIN".equals(userRole)) {
            throw new FailedToGetJobPostException("Only ADMIN can delete job posts");
        }

        JobPost jobPost = repository.findById(id)
                .orElseThrow(() -> new FailedToGetJobPostException("Job post not found with id " + id));

        repository.delete(jobPost);
    }

    @Override
    public GetJobPostResponse updateJobPost(Long id, UpdateJobPostRequest request, String userRole) throws FailedToGetJobPostException {
        if (!"ADMIN".equals(userRole)) {
            throw new FailedToGetJobPostException("Only ADMIN can update job posts");
        }

        JobPost jobPost = repository.findById(id)
                .orElseThrow(() -> new FailedToGetJobPostException("Job post not found with id " + id));

        // Update fields as per the request
        jobPost.setJobTitle(request.getJobTitle());
        jobPost.setJobType(request.getJobType());
        jobPost.setSalaryRange(request.getSalaryRange());
        jobPost.setJobDescription(request.getJobDescription());
        jobPost.setQualifications(request.getQualifications());
        jobPost.setExperience(request.getExperience());
        jobPost.setSkillsRequired(request.getSkillsRequired());
        jobPost.setApplicationProcess(request.getApplicationProcess());
        jobPost.setApplicationDeadline(request.getApplicationDeadline());
        jobPost.setBenefits(request.getBenefits());

        repository.save(jobPost);

        return convertToResponse(jobPost);
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

    private CreateJobPostResponse buildResponse(JobPost savedJobPost) {
        return CreateJobPostResponse.builder()
                .message("Job Post Created Successfully")
                .jobTitle(savedJobPost.getJobTitle())
                .jobId(savedJobPost.getId())
                .userId(savedJobPost.getUserid())
                .build();
    }
}
