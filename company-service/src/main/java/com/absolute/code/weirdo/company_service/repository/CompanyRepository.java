package com.absolute.code.weirdo.company_service.repository;

import com.absolute.code.weirdo.company_service.exceptions.CompanyNotFoundException;
import com.absolute.code.weirdo.company_service.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByName(String name) throws CompanyNotFoundException;
    List<Company> findByIndustry(String industry) throws CompanyNotFoundException;

}
