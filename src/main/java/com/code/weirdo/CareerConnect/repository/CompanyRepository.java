package com.code.weirdo.CareerConnect.repository;

import com.code.weirdo.CareerConnect.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findCompanyByName(String name) throws Exception;
    List<Company> findCompanyByIndustry(String industry) throws Exception;
}
