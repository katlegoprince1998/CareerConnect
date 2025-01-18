package com.absolute.code.weirdo.company_service.response;

import com.absolute.code.weirdo.company_service.model.ContactInfo;
import com.absolute.code.weirdo.company_service.model.Location;

public record CompanyResponse (Long companyId,
                               String name,
                               String logo,
                               String website,
                               String industry,
                               String description,
                               Location location,
                               ContactInfo contactInfo,
                               Long userId) {}
