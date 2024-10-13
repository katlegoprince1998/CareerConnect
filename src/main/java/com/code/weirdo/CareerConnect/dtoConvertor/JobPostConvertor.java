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
                .companyContactInfo(jobPost.getCompanyContactInfo())
                .companyName(jobPost.getCompanyName())
                .companyOverview(jobPost.getCompanyOverview())
                .experience(jobPost.getExperience())
                .jobTitle(jobPost.getJobTitle())
                .skillsRequired(jobPost.getSkillsRequired())
                .salaryRange(jobPost.getSalaryRange())
                .location(jobPost.getLocation())
                .postCreatedAt(jobPost.getPostCreatedAt())
                .qualifications(jobPost.getQualifications())
                .build();
    }

    public static JobPost convertToEntity(JobPostDto dto){
        return JobPost
                .builder()
                .applicationDeadLine(dto.getApplicationDeadLine())
                .jobDescription(dto.getJobDescription())
                .applicationProcess(dto.getApplicationProcess())
                .benefits(dto.getBenefits())
                .companyContactInfo(dto.getCompanyContactInfo())
                .companyName(dto.getCompanyName())
                .companyOverview(dto.getCompanyOverview())
                .experience(dto.getExperience())
                .jobTitle(dto.getJobTitle())
                .skillsRequired(dto.getSkillsRequired())
                .salaryRange(dto.getSalaryRange())
                .location(dto.getLocation())
                .postCreatedAt(dto.getPostCreatedAt())
                .qualifications(dto.getQualifications())
                .build();
    }
}
