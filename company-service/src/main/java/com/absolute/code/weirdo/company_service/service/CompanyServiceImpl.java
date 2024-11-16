package com.absolute.code.weirdo.company_service.service;

import com.absolute.code.weirdo.company_service.exceptions.CompanyNotFoundException;
import com.absolute.code.weirdo.company_service.exceptions.FailedToCreateCompanyException;
import com.absolute.code.weirdo.company_service.model.Company;
import com.absolute.code.weirdo.company_service.model.ContactInfo;
import com.absolute.code.weirdo.company_service.model.Location;
import com.absolute.code.weirdo.company_service.repository.CompanyRepository;
import com.absolute.code.weirdo.company_service.request.CompanyRequest;
import com.absolute.code.weirdo.company_service.response.CompanyResponse;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService{
    private final CompanyRepository repository;

    public CompanyServiceImpl(CompanyRepository repository){
        this.repository = repository;
    }

    @Override
    public CompanyResponse createCompany(CompanyRequest request, Long userId, String userRole) {
        if (request == null)
            throw new IllegalArgumentException("Failed to create company | request object is null: " + null);

        try{
           if (!"ADMIN".equals(userRole))
               throw new FailedToCreateCompanyException("Only users with admin roles can add companies: " + userRole);

            Company createCompany = buildCompany(request, userId);
            Company savedCompany = repository.save(createCompany);

            return returnCompany(savedCompany);

        }catch (FailedToCreateCompanyException e){
          throw new FailedToCreateCompanyException("Failed to create company");
        }
    }

    @Override
    public CompanyResponse findByName(String name) throws CompanyNotFoundException {
        if (name.isEmpty())
            throw new IllegalArgumentException("Name object cannot be empty or null");
        try{
            Company company = repository.findByName(name);

            if (company == null)
                throw new CompanyNotFoundException("Company was not found");

            return returnCompany(company);
        }catch (CompanyNotFoundException e){
            throw new CompanyNotFoundException("Company was not found");
        }
    }

    @Override
    public List<CompanyResponse> findByIndustry(String industry) throws CompanyNotFoundException {
        if (industry.isEmpty())
            throw new IllegalArgumentException("Industry name cannot be empty or null " + industry);

        try{
            List<Company> companies = repository.findByIndustry(industry);

            if (companies == null || companies.isEmpty())
                throw new CompanyNotFoundException("Companies with " + industry + " do not exist");

            return companies
                    .stream()
                    .map(this::returnCompany)
                    .collect(Collectors.toList());
        }catch (CompanyNotFoundException e){
            throw new CompanyNotFoundException("Failed to get companies by industry");
        }
    }

    @Override
    public List<CompanyResponse> findAllCompanies() {
        try{
           List<Company> companies = repository.findAll();

           return companies
                   .stream()
                   .map(this::returnCompany)
                   .collect(Collectors.toList());
        }catch (CompanyNotFoundException e){
            throw new CompanyNotFoundException("Failed to get list of companies");
        }
    }

    @Override
    public CompanyResponse updateCompany(CompanyRequest request, Long id, String userRole) throws CompanyNotFoundException {
        if (request == null) {
            throw new IllegalArgumentException("Company request cannot be null");
        }

        if (!"ADMIN".equals(userRole))
            throw new FailedToCreateCompanyException("Only users with admin roles can update companies: " + userRole);

        // Validate ID
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid company ID: " + id);
        }

        try {
            // Fetch the existing company or throw exception if not found
            Company existingCompany = repository.findById(id)
                    .orElseThrow(() -> new CompanyNotFoundException("Company with ID " + id + " was not found"));

            // Update fields if valid values are provided
            updateCompanyFields(existingCompany, request);

            // Save the updated company
            Company updatedCompany = repository.save(existingCompany);

            // Convert the updated company to CompanyResponse and return
            return returnCompany(updatedCompany);
        } catch (CompanyNotFoundException e) {
            throw new CompanyNotFoundException("Failed to update company");
        }
    }

    @Override
    public String deleteCompany(Long id, String userRole) throws CompanyNotFoundException {

        if (!"ADMIN".equals(userRole))
            throw new FailedToCreateCompanyException("Only users with admin roles can delete companies: " + userRole);
        // Validate ID
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid company ID: " + id);
        }

        try {
            // Check if the company exists
            Company company = repository.findById(id)
                    .orElseThrow(() -> new CompanyNotFoundException("Company with ID " + id + " was not found"));

            // Delete the company
            repository.delete(company);

            // Return confirmation message
            return "Company with ID " + id + " was successfully deleted.";
        } catch (CompanyNotFoundException e) {
            throw new CompanyNotFoundException("Failed to delete company");
        }
    }


    private static Company buildCompany(CompanyRequest request, Long userId){
        return Company
                .builder()
                .logo(request.getLogo())
                .name(request.getName())
                .description(request.getDescription())
                .website(request.getWebsite())
                .industry(request.getIndustry())
                .location(new Location(request.getCountry(), request.getProvince(),  request.getCity(), request.getAddress()
                        , request.getStreetName(), request.getPostalCode()))
                .contactInfo(new ContactInfo(request.getEmail(), request.getPhone()))
                .userId(userId)
                .build();
    }

    // Helper method to update fields if they are not null or empty
    private void updateCompanyFields(Company existingCompany, CompanyRequest request) {
        if (isNotNullOrEmpty(request.getName())) {
            existingCompany.setName(request.getName());
        }
        if (isNotNullOrEmpty(request.getLogo())) {
            existingCompany.setLogo(request.getLogo());
        }
        if (isNotNullOrEmpty(request.getWebsite())) {
            existingCompany.setWebsite(request.getWebsite());
        }
        if (isNotNullOrEmpty(request.getIndustry())) {
            existingCompany.setIndustry(request.getIndustry());
        }
        if (isNotNullOrEmpty(request.getDescription())) {
            existingCompany.setDescription(request.getDescription());
        }
        if (isLocationValid(request)) {
            existingCompany.setLocation(new Location(
                    request.getCountry(),
                    request.getProvince(),
                    request.getCity(),
                    request.getAddress(),
                    request.getStreetName(),
                    request.getPostalCode()
            ));
        }
        if (isContactInfoValid(request)) {
            existingCompany.setContactInfo(new ContactInfo(
                    request.getEmail(),
                    request.getPhone()
            ));
        }
    }

    // Utility method to check if a string is not null or empty
    private boolean isNotNullOrEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    // Validate location fields
    private boolean isLocationValid(CompanyRequest request) {
        return isNotNullOrEmpty(request.getCountry())
                || isNotNullOrEmpty(request.getProvince())
                || isNotNullOrEmpty(request.getCity())
                || isNotNullOrEmpty(request.getAddress())
                || isNotNullOrEmpty(request.getStreetName())
                || isNotNullOrEmpty(request.getPostalCode());
    }

    // Validate contact info fields
    private boolean isContactInfoValid(CompanyRequest request) {
        return isNotNullOrEmpty(request.getEmail()) || isNotNullOrEmpty(request.getPhone());
    }

    private  CompanyResponse returnCompany(Company savedCompany){
        return new CompanyResponse(savedCompany.getCompanyId(), savedCompany.getName(), savedCompany.getLogo(),
                savedCompany.getWebsite(), savedCompany.getIndustry(), savedCompany.getDescription(),
                savedCompany.getLocation(), savedCompany.getContactInfo(), savedCompany.getUserId());
    }
}
