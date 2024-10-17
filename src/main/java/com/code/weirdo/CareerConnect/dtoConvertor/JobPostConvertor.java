package com.code.weirdo.CareerConnect.dtoConvertor;

import com.code.weirdo.CareerConnect.dto.JobPostDto;
import com.code.weirdo.CareerConnect.models.JobPost;

public class JobPostConvertor {
    private JobPostConvertor(){}
    // convert entity to dto
    public static JobPostDto convertToDto(JobPost jobPost){
        return JobPostDto
                .builder()
                .applicationDeadLine(jobPost.getApplicationDeadLine())
                .jobDescription(jobPost.getJobDescription())
                .applicationProcess(jobPost.getApplicationProcess())
                .benefits(jobPost.getBenefits())
                .experience(jobPost.getExperience())
                .jobTitle(jobPost.getJobTitle())
                .jobType(jobPost.getJobType())
                .skillsRequired(jobPost.getSkillsRequired())
                .salaryRange(jobPost.getSalaryRange())
                .postCreatedAt(jobPost.getPostCreatedAt())
                .qualifications(jobPost.getQualifications())
                .company(jobPost.getCompanyInformation())
                .build();
    }

    public static JobPost convertToEntity(JobPostDto dto){
        return JobPost
                .builder()
                .applicationDeadLine(dto.getApplicationDeadLine())
                .jobDescription(dto.getJobDescription())
                .applicationProcess(dto.getApplicationProcess())
                .benefits(dto.getBenefits())
                .experience(dto.getExperience())
                .jobType(dto.getJobType())
                .jobTitle(dto.getJobTitle())
                .skillsRequired(dto.getSkillsRequired())
                .salaryRange(dto.getSalaryRange())
                .postCreatedAt(dto.getPostCreatedAt())
                .qualifications(dto.getQualifications())
                .companyInformation(dto.getCompany())
                .build();
    }
}
