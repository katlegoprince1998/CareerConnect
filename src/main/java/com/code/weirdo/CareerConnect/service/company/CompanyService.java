package com.code.weirdo.CareerConnect.service.company;

import com.code.weirdo.CareerConnect.dto.CompanyDto;

import java.util.List;

public interface CompanyService {
    CompanyDto findByName(String name) throws Exception;
    List<CompanyDto> findByIndustry(String industry) throws Exception;
    CompanyDto addCompany(CompanyDto dto) throws Exception;
    CompanyDto updateCompanyInfo(CompanyDto dto, Long companyId) throws Exception;
    void removeCompany(Long companyId) throws Exception;
    CompanyDto findById(Long companyId) throws Exception;

}
