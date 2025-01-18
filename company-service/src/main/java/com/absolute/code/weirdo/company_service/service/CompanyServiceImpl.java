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

    @Override
    public CompanyResponse findById(Long id) throws CompanyNotFoundException {
       if (id == null)
           throw new IllegalArgumentException("Company Id is required to retrieve a company");
       try{
          Company company = repository.findById(id).orElseThrow(() -> new CompanyNotFoundException("Company with ID "
                  + id + " was not found"));

          return returnCompany(company);
       }catch (CompanyNotFoundException e){
           throw new CompanyNotFoundException("Failed to retrieve a company with " + id + " id ");
       }
    }


    private static Company buildCompany(CompanyRequest request, Long userId){
        return Company
                .builder()
                .logo(request.logo())
                .name(request.name())
                .description(request.description())
                .website(request.website())
                .industry(request.industry())
                .location(new Location(request.country(), request.province(),  request.city(), request.address()
                        , request.streetName(), request.postalCode()))
                .contactInfo(new ContactInfo(request.email(), request.phone()))
                .userId(userId)
                .build();
    }

    // Helper method to update fields if they are not null or empty
    private void updateCompanyFields(Company existingCompany, CompanyRequest request) {
        if (isNotNullOrEmpty(request.name())) {
            existingCompany.setName(request.name());
        }
        if (isNotNullOrEmpty(request.logo())) {
            existingCompany.setLogo(request.logo());
        }
        if (isNotNullOrEmpty(request.website())) {
            existingCompany.setWebsite(request.website());
        }
        if (isNotNullOrEmpty(request.industry())) {
            existingCompany.setIndustry(request.industry());
        }
        if (isNotNullOrEmpty(request.description())) {
            existingCompany.setDescription(request.description());
        }
        if (isLocationValid(request)) {
            existingCompany.setLocation(new Location(
                    request.country(),
                    request.province(),
                    request.city(),
                    request.address(),
                    request.streetName(),
                    request.postalCode()
            ));
        }
        if (isContactInfoValid(request)) {
            existingCompany.setContactInfo(new ContactInfo(
                    request.email(),
                    request.phone()
            ));
        }
    }

    // Utility method to check if a string is not null or empty
    private boolean isNotNullOrEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    // Validate location fields
    private boolean isLocationValid(CompanyRequest request) {
        return isNotNullOrEmpty(request.country())
                || isNotNullOrEmpty(request.province())
                || isNotNullOrEmpty(request.city())
                || isNotNullOrEmpty(request.address())
                || isNotNullOrEmpty(request.streetName())
                || isNotNullOrEmpty(request.postalCode());
    }

    // Validate contact info fields
    private boolean isContactInfoValid(CompanyRequest request) {
        return isNotNullOrEmpty(request.email()) || isNotNullOrEmpty(request.phone());
    }

    private  CompanyResponse returnCompany(Company savedCompany){
        return new CompanyResponse(savedCompany.getCompanyId(), savedCompany.getName(), savedCompany.getLogo(),
                savedCompany.getWebsite(), savedCompany.getIndustry(), savedCompany.getDescription(),
                savedCompany.getLocation(), savedCompany.getContactInfo(), savedCompany.getUserId());
    }
}
