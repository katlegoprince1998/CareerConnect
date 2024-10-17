package com.code.weirdo.CareerConnect.dtoConvertor;


import com.code.weirdo.CareerConnect.dto.CompanyDto;
import com.code.weirdo.CareerConnect.models.Company;

public class CompanyConvertor {
    private CompanyConvertor(){}

    public static CompanyDto toDto(Company company){
        return CompanyDto
                .builder()
                .name(company.getName())
                .logo(company.getLogo())
                .location(company.getLocation())
                .contactInfo(company.getContactInfo())
                .website(company.getWebsite())
                .description(company.getDescription())
                .industry(company.getIndustry())
                .build();
    }

    public static Company toEntity(CompanyDto dto){
        return Company
                .builder()
                .name(dto.getName())
                .location(dto.getLocation())
                .contactInfo(dto.getContactInfo())
                .website(dto.getWebsite())
                .description(dto.getDescription())
                .industry(dto.getIndustry())
                .build();
    }
}
