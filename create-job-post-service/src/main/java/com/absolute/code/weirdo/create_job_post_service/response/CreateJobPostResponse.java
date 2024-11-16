package com.absolute.code.weirdo.create_job_post_service.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CreateJobPostResponse {
    private Long jobId;
    private Long userId;
    private String jobTitle;
    private Long companyId;
    private String message;
}
