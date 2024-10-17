package com.code.weirdo.CareerConnect.dto;

import com.code.weirdo.CareerConnect.models.Company;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobPostDto {
    private String jobTitle;
    private String jobType;
    private String salaryRange;
    private String jobDescription;
    private String qualifications;
    private String experience;
    private String skillsRequired;
    private String companyOverview;
    private String applicationProcess;
    private LocalDate applicationDeadLine;
    private String benefits;
    private LocalDate postCreatedAt;
    private Company company;
}
