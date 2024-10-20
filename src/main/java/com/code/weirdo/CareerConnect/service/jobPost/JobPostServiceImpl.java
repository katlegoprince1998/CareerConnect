package com.code.weirdo.CareerConnect.service.jobPost;

import com.code.weirdo.CareerConnect.dto.CompanyDto;
import com.code.weirdo.CareerConnect.dto.JobPostDto;
import com.code.weirdo.CareerConnect.dtoConvertor.CompanyConvertor;
import com.code.weirdo.CareerConnect.dtoConvertor.JobPostConvertor;
import com.code.weirdo.CareerConnect.models.JobPost;
import com.code.weirdo.CareerConnect.repository.JobPostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JobPostServiceImpl implements JobPostService{
    private final JobPostRepository repository;

    @Override
    public JobPostDto createJobPost(JobPostDto dto, CompanyDto companyDto) throws Exception {
        if (dto == null)
            throw new Exception("JobPostDto is empty");

        if (companyDto == null)
            throw new Exception("CompanyDto is empty");

        // Convert DTO to entity
        JobPost jobPost = JobPostConvertor.convertToEntity(dto);

        // Associate the job post with the company
        jobPost.setCompany(CompanyConvertor.toEntity(companyDto));

        // Save the job post
        JobPost createdPost = repository.save(jobPost);

        // Convert saved entity back to DTO and return
        return JobPostConvertor.convertToDto(createdPost);
    }


    @Override
    public JobPostDto updateJobPost(JobPostDto dto, Long id) throws Exception {
        Optional<JobPost> optionalJobPost = repository.findById(id);
        if (optionalJobPost.isEmpty()) {
            throw new Exception("Job post with Id: " + id + " not found");
        }
        JobPost existingJobPost = optionalJobPost.get();
        // Save the updated JobPost back to the repository
        repository.save(updateJobPostFields(dto, existingJobPost));

        return JobPostConvertor.convertToDto(existingJobPost);
    }

    @Override
    public JobPostDto getJobPostById(Long id) throws Exception {
        Optional<JobPost> optionalJobPost = repository.findById(id);
        if (optionalJobPost.isEmpty())
            throw new Exception("Job post with this Id: " + id + " was not found");
        JobPost jobPost = optionalJobPost.get();
        return JobPostConvertor.convertToDto(jobPost);
    }

    @Override
    public List<JobPostDto> getAllJobPosts() {
        List<JobPost> data = repository.findAll();
        return data.stream()
                .map(JobPostConvertor::convertToDto) // Use method reference to convert each JobPost
                .collect(Collectors.toList());
    }

    @Override
    public void deleteJobPost(Long id) throws Exception {
        Optional<JobPost> optionalJobPost = repository.findById(id);
        if (optionalJobPost.isEmpty())
            throw new Exception("Job post with this Id: " + id + " was not found");
        JobPost jobPost = optionalJobPost.get();
        repository.delete(jobPost);
    }

    @Override
    public List<JobPostDto> filterJobs(String jobTitle, String jobType, String salaryRange) {
        try {
            return repository.filterJobs(jobTitle, jobType, salaryRange)
                    .stream()
                    .map(JobPostConvertor::convertToDto)  // Convert each JobPost to JobPostDto using the converter
                    .collect(Collectors.toList());       // Collect the results into a List
        } catch (Exception e) {
            return new ArrayList<>(); // Return an empty list in case of error
        }
    }



    private static JobPost updateJobPostFields(JobPostDto dto, JobPost existingJobPost) {
        updateField(dto.getJobTitle(), existingJobPost::setJobTitle);
        updateField(dto.getJobType(), existingJobPost::setJobType);
        updateField(dto.getSalaryRange(), existingJobPost::setSalaryRange);
        updateField(dto.getJobDescription(), existingJobPost::setJobDescription);
        updateField(dto.getQualifications(), existingJobPost::setQualifications);
        updateField(dto.getExperience(), existingJobPost::setExperience);
        updateField(dto.getSkillsRequired(), existingJobPost::setSkillsRequired);
        updateField(dto.getApplicationProcess(), existingJobPost::setApplicationProcess);
        updateField(dto.getApplicationDeadLine(), existingJobPost::setApplicationDeadLine);
        updateField(dto.getBenefits(), existingJobPost::setBenefits);
        updateField(dto.getPostCreatedAt(), existingJobPost::setPostCreatedAt);



        return existingJobPost;
    }

    private static <T> void updateField(T newValue, Consumer<T> setter) {
        if (newValue != null && !((newValue instanceof String) && ((String) newValue).isEmpty())) {
            setter.accept(newValue);
        }
    }

}
