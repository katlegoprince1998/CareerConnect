package com.absolute.code.weirdo.company_service.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequest {
    private String name;
    private String logo;
    private String website;
    private String description;
    private String industry;
    private String country;
    private String province;
    private String city;
    private String address;
    private String streetName;
    private String postalCode;
    private String email;
    private String phone;
}
