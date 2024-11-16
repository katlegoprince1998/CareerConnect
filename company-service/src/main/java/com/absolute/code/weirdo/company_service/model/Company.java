package com.absolute.code.weirdo.company_service.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
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
    private ContactInfo contactInfo;
    private Long userId;
}
