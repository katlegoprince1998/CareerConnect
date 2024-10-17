package com.code.weirdo.CareerConnect.service.company;

import com.code.weirdo.CareerConnect.dto.CompanyDto;
import com.code.weirdo.CareerConnect.dtoConvertor.CompanyConvertor;
import com.code.weirdo.CareerConnect.models.Company;
import com.code.weirdo.CareerConnect.repository.CompanyRepository;
import com.code.weirdo.CareerConnect.validators.CompanyField;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService{
    private final CompanyRepository repository;

    @Override
    public CompanyDto findByName(String name) throws Exception {
        if (name.isEmpty())
            throw new Exception("Company name is required");
        try{
            Company company = repository.findCompanyByName(name);
            if (company != null)
                return CompanyConvertor.toDto(company);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<CompanyDto> findByIndustry(String industry) throws Exception {
        if(industry.isEmpty())
            throw new Exception("Industry field is required");
        try {
            List<Company> companies = repository.findCompanyByIndustry(industry);
            if (companies != null)
                return companies
                        .stream()
                        .map(CompanyConvertor::toDto)
                        .toList();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
       return null;
    }

    @Override
    public CompanyDto addCompany(CompanyDto dto) throws Exception {
        if (dto == null)
            throw new Exception("Object is null");
        try{
            Company company = CompanyConvertor.toEntity(dto);
            Company savedCompany = repository.save(company);
            return CompanyConvertor.toDto(savedCompany);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public CompanyDto updateCompanyInfo(CompanyDto dto, Long companyId) throws Exception {
        // Retrieve the existing company record from the repository
        Optional<Company> companyOptional = repository.findById(companyId);
        if (companyOptional.isEmpty()) {
            throw new Exception("Company not found with ID: " + companyId);
        }

        Company company = companyOptional.get();

        // Use enums to update only non-null and non-empty fields
        updateFieldIfPresent(CompanyField.NAME, company, dto.getName());
        updateFieldIfPresent(CompanyField.LOGO, company, dto.getLogo());
        updateFieldIfPresent(CompanyField.WEBSITE, company, dto.getWebsite());
        updateFieldIfPresent(CompanyField.INDUSTRY, company, dto.getIndustry());
        updateFieldIfPresent(CompanyField.DESCRIPTION, company, dto.getDescription());

        // Save the updated company back to the repository
        Company updatedCompany = repository.save(company);

        // Map the updated company entity back to a DTO (assuming you have a mapping function)
        return CompanyConvertor.toDto(updatedCompany);
    }

    private void updateFieldIfPresent(CompanyField field, Company company, String value) {
        if (value != null && !value.isEmpty()) {
            field.update(company, value);
        }
    }

    @Override
    @Transactional
    public void removeCompany(Long companyId) throws Exception {
        // Check if the company exists
        Optional<Company> companyOptional = repository.findById(companyId);
        if (companyOptional.isEmpty()) {
            throw new Exception("Company not found with ID: " + companyId);
        }
        // Remove the company
        try {
            repository.deleteById(companyId);
        } catch (Exception e) {
            throw new Exception("Error occurred while removing company with ID: " + companyId, e);
        }
    }

    @Override
    public CompanyDto findById(Long companyId) throws Exception {
        // Check if the company exists
        Optional<Company> companyOptional = repository.findById(companyId);
        if (companyOptional.isEmpty()) {
            throw new Exception("Company not found with ID: " + companyId);
        }
        // Remove the company
        try {
           Company company = companyOptional.get();
           return CompanyConvertor.toDto(company);
        } catch (Exception e) {
            throw new Exception("Error occurred while retrieving company with ID: " + companyId, e);
        }
    }
}
