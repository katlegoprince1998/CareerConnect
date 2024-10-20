package com.code.weirdo.CareerConnect.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class JobPost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String jobTitle;
    private String jobType;
    private String salaryRange;
    private String jobDescription;
    private String qualifications;
    private String experience;
    private String skillsRequired;
    private String applicationProcess;
    private LocalDate applicationDeadLine;
    private String benefits;
    private LocalDate postCreatedAt;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "companyId")
    private Company company;


}
