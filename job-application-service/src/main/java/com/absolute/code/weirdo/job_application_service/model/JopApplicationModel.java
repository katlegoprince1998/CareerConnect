package com.absolute.code.weirdo.job_application_service.model;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JopApplicationModel {
    private Long id;
    private Long userId;
    private Long jobId;
    private LocalDate createdAt;
    private String status;
}
