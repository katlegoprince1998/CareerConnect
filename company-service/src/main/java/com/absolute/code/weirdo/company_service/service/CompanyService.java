package com.absolute.code.weirdo.company_service.service;

import com.absolute.code.weirdo.company_service.exceptions.CompanyNotFoundException;
import com.absolute.code.weirdo.company_service.request.CompanyRequest;
import com.absolute.code.weirdo.company_service.response.CompanyResponse;

import java.util.List;

public interface CompanyService {
    CompanyResponse createCompany(CompanyRequest request, Long userId, String userRole);
    CompanyResponse findByName(String name) throws CompanyNotFoundException;
    List<CompanyResponse> findByIndustry(String industry) throws CompanyNotFoundException;
    List<CompanyResponse> findAllCompanies();
    CompanyResponse updateCompany(CompanyRequest request, Long id, String userRole) throws CompanyNotFoundException;
    String deleteCompany(Long id, String userRole) throws CompanyNotFoundException;
    CompanyResponse findById(Long id) throws CompanyNotFoundException;
}
