package com.code.weirdo.CareerConnect.dto;

import com.code.weirdo.CareerConnect.models.data.CompanyContactInfo;
import com.code.weirdo.CareerConnect.models.data.Location;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    private String name;
    private String logo;
    private String city;
    private String website;
    private String industry;
    private String description;
    private Location location;
    private CompanyContactInfo contactInfo;
}
