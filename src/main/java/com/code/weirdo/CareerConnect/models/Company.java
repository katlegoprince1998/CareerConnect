package com.code.weirdo.CareerConnect.models;

import com.code.weirdo.CareerConnect.models.data.CompanyContactInfo;
import com.code.weirdo.CareerConnect.models.data.Location;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long companyId;
    private String name;
    private String logo;
    private String website;
    private String industry;
    private String description;
    @Embedded
    private Location location;
    @Embedded
    private CompanyContactInfo contactInfo;
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobPost> jobPosts;

}
