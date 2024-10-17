package com.code.weirdo.CareerConnect.service.company;

import com.code.weirdo.CareerConnect.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService{
    private final CompanyRepository repository;
}
