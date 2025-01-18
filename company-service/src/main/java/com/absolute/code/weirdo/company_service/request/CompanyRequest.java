package com.absolute.code.weirdo.company_service.request;


public record CompanyRequest(
        String name,
        String logo,
        String website,
        String description,
        String industry,
        String country,
        String province,
        String city,
        String address,
        String streetName,
        String postalCode,
        String email,
        String phone
) {}

