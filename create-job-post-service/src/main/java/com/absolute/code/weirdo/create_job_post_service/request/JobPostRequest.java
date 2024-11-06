package com.absolute.code.weirdo.create_job_post_service.request;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobPostRequest {
    private String jobTitle;
    private String jobType;
    private String salaryRange;
    private String jobDescription;
    private String qualifications;
    private String experience;
    private String skillsRequired;
    private String applicationProcess;
    private String applicationDeadline;
    private String benefits;
}
