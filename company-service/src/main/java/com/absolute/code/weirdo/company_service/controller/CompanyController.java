package com.absolute.code.weirdo.company_service.controller;

import com.absolute.code.weirdo.company_service.dto.UserDto;
import com.absolute.code.weirdo.company_service.exceptions.CompanyNotFoundException;
import com.absolute.code.weirdo.company_service.request.CompanyRequest;
import com.absolute.code.weirdo.company_service.response.CompanyResponse;
import com.absolute.code.weirdo.company_service.service.CompanyService;
import com.absolute.code.weirdo.company_service.service.user.UserService;
import org.apache.hc.client5.http.auth.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);
    private final CompanyService companyService;
    private final UserService userService;

    public CompanyController(CompanyService companyService, UserService userService) {
        this.companyService = companyService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(@RequestBody CompanyRequest request,
                                                         @RequestHeader(value = "Authorization", required = false) String jwt)
            throws AuthenticationException {
        // Authenticate and authorize user
        UserDto user = authenticateUser(jwt);
        authorizeUser(user, "ADMIN");

        logger.info("User [{}] with role [{}] attempting to create a company.", user.userId(), user.role());

        CompanyResponse response = companyService.createCompany(request, user.userId(), user.role());
        logger.info("Company created successfully: {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/company-name")
    public ResponseEntity<CompanyResponse> findCompanyByName(@RequestParam String name,
                                                             @RequestHeader(value = "Authorization",
                                                                     required = false) String jwt)
            throws AuthenticationException {
        authenticateUser(jwt); // Optional: Allow all roles to access this endpoint
        logger.info("Searching for company by name: {}", name);

        CompanyResponse response = companyService.findByName(name);
        logger.info("Company found: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/industry")
    public ResponseEntity<List<CompanyResponse>> findCompaniesByIndustry(@RequestParam String industry,
                                                                         @RequestHeader(value = "Authorization", required = false) String jwt)
            throws AuthenticationException {
        authenticateUser(jwt); // Optional: Allow all roles to access this endpoint
        logger.info("Searching for companies in industry: {}", industry);

        List<CompanyResponse> response = companyService.findByIndustry(industry);
        logger.info("Found {} companies in industry: {}", response.size(), industry);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> findAllCompanies(@RequestHeader(value = "Authorization", required = false) String jwt)
            throws AuthenticationException {
        authenticateUser(jwt); // Optional: Allow all roles to access this endpoint
        logger.info("Fetching all companies.");

        List<CompanyResponse> response = companyService.findAllCompanies();
        logger.info("Found {} companies.", response.size());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> updateCompany(@RequestBody CompanyRequest request,
                                                         @PathVariable Long id,
                                                         @RequestHeader(value = "Authorization", required = false) String jwt)
            throws AuthenticationException {
        UserDto user = authenticateUser(jwt);
        authorizeUser(user, "ADMIN");

        logger.info("User [{}] with role [{}] updating company with ID: {}", user.userId(), user.role(), id);

        CompanyResponse response = companyService.updateCompany(request, id, user.role());
        logger.info("Company updated successfully: {}", response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id,
                                                @RequestHeader(value = "Authorization", required = false) String jwt)
            throws AuthenticationException {
        UserDto user = authenticateUser(jwt);
        authorizeUser(user, "ADMIN");

        logger.info("User [{}] with role [{}] deleting company with ID: {}", user.userId(), user.role(), id);

        String response = companyService.deleteCompany(id, user.role());
        logger.info(response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> findCompanyById(@PathVariable Long id) {
        logger.info("Searching for company with ID: {}", id);

        try {
            CompanyResponse response = companyService.findById(id);
            logger.info("Found company: {}", response);
            return ResponseEntity.ok(response);
        } catch (CompanyNotFoundException e) {
            logger.error("Company not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid request: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Helper method to authenticate user via JWT
    private UserDto authenticateUser(String jwt) throws AuthenticationException {
        if (jwt == null || jwt.isEmpty()) {
            throw new AuthenticationException("Missing or invalid Authorization token.");
        }

        UserDto user = userService.getUserProfile(jwt);
        if (user == null) {
            throw new AuthenticationException("Invalid token or user does not exist.");
        }

        return user;
    }

    // Helper method to authorize user based on role
    private void authorizeUser(UserDto user, String requiredRole) throws AuthenticationException {
        if (!requiredRole.equals(user.role())) {
            throw new AuthenticationException("User does not have the required role: " + requiredRole);
        }
    }
}
