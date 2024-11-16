package com.absolute.code.weirdo.create_job_post_service.service.company;

import com.absolute.code.weirdo.create_job_post_service.dto.CompanyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "COMPANY-SERVICE", url = "http://localhost:5003")
public interface CompanyService {
    @GetMapping("/api/v1/companies/{id}")
    CompanyDto getCompany(@RequestHeader("Authorization") String jwt, @PathVariable Long id);
}
