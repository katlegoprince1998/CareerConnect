package com.absolute.code.weirdo.create_job_post_service.dto;

public record CompanyDto(
        Long companyId,
        String name,
        String industry,
        String logo,
        String website,
        String description
) {
}
