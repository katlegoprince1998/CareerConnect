package com.code.weirdo.CareerConnect.models;

import com.code.weirdo.CareerConnect.models.data.CompanyContactInfo;
import com.code.weirdo.CareerConnect.models.data.Location;
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
    private String companyName;
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
    @Embedded
    private CompanyContactInfo companyContactInfo;
    @Embedded
    private Location location;
}
