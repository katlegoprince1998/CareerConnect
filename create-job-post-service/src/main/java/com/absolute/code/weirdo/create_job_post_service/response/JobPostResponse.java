package com.absolute.code.weirdo.create_job_post_service.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class JobPostResponse {
    private Long jobId;
    private Long userId;
    private String jobTitle;
    private String message;
}
