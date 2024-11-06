package com.absolute.code.weirdo.create_job_post_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class JobPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Long userid;
}
