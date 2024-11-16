package com.absolute.code.weirdo.company_service.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Location {
    private String country;
    private String province;
    private String city;
    private String address;
    private String streetName;
    private String postalCode;
}
