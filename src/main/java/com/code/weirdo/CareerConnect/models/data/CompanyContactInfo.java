package com.code.weirdo.CareerConnect.models.data;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class CompanyContactInfo {
    private String email;
    private String cellNumber;
}
