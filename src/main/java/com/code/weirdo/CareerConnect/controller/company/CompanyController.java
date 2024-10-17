package com.code.weirdo.CareerConnect.controller.company;

import com.code.weirdo.CareerConnect.dto.CompanyDto;
import com.code.weirdo.CareerConnect.service.company.CompanyService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
public class CompanyController {
    private CompanyService service;

    private final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @GetMapping("/find-company-by-name/{name}")
    public ResponseEntity<CompanyDto> findByName(@PathVariable String name) throws BadRequestException {
        if (name == null || name.isEmpty()) {
            throw new BadRequestException("Company name is required");
        }
        try {
            CompanyDto companyDto = service.findByName(name);
            return new ResponseEntity<>(companyDto, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while finding company by name", e);
            throw new BadRequestException("Bad request");
        }
    }

    @GetMapping("/find-company-by-name/{industry}")
    public ResponseEntity<List<CompanyDto>> findByIndustry(@PathVariable String industry) throws Exception {
        if (industry == null || industry.isEmpty()) {
            throw new BadRequestException("Industry field is required");
        }
        try {
            List<CompanyDto> companies = service.findByIndustry(industry);
            if (companies.isEmpty()) {
                throw new ChangeSetPersister.NotFoundException();
            }
            return ResponseEntity.ok(companies);
        } catch (Exception e) {
            logger.error("Error while finding companies by industry", e);
            throw new Exception("Error finding companies by industry");
        }
    }

    @PostMapping("/add-company")
    public ResponseEntity<CompanyDto> addCompany(@RequestBody CompanyDto companyDto) throws Exception {
        if (companyDto == null) {
            throw new BadRequestException("Company object is required");
        }
        try {
            CompanyDto savedCompany = service.addCompany(companyDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCompany);
        } catch (Exception e) {
            logger.error("Error while adding company", e);
            throw new Exception("Error adding company");
        }
    }

    @PutMapping("/update-company/{id}")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable Long id, @RequestBody CompanyDto companyDto) throws Exception {
        if (companyDto == null) {
            throw new BadRequestException("Company object is required");
        }
        try {
            CompanyDto updatedCompany = service.updateCompanyInfo(companyDto, id);
            return ResponseEntity.ok(updatedCompany);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw e; // propagate the custom NotFoundException for proper handling
        } catch (Exception e) {
            logger.error("Error while updating company", e);
            throw new Exception("Error updating company");
        }
    }

    @DeleteMapping("/remove-company/{id}")
    @Transactional
    public ResponseEntity<Void> removeCompany(@PathVariable Long id) throws Exception {
        try {
            service.removeCompany(id);
            return ResponseEntity.noContent().build();
        } catch (ChangeSetPersister.NotFoundException e) {
            throw e; // propagate custom NotFoundException
        } catch (Exception e) {
            logger.error("Error while removing company", e);
            throw new Exception("Error removing company");
        }
    }
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<CompanyDto> findById(@PathVariable Long id) throws Exception {
        if (id == null || id <= 0) {
            throw new BadRequestException("Valid company ID is required");
        }
        try {
            CompanyDto companyDto = service.findById(id);
            return ResponseEntity.ok(companyDto);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw e;  // propagate NotFoundException
        } catch (Exception e) {
            logger.error("Error while finding company by ID", e);
            throw new Exception("Error retrieving company by ID");
        }
    }
}
