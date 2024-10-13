package com.code.weirdo.CareerConnect.models.data;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Location {
    private String city;
    private String province;
    private String address;
    private String streetName;
    private String postalCode;
}
