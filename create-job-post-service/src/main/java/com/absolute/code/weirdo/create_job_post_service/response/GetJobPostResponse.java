package com.absolute.code.weirdo.create_job_post_service.response;

import com.absolute.code.weirdo.create_job_post_service.dto.UserDto;
import lombok.*;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class GetJobPostResponse {
    private Long id;
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
    private LocalDate postCreatedAt;
    private UserDto userDto;
}
