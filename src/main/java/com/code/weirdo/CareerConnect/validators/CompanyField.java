package com.code.weirdo.CareerConnect.validators;

import com.code.weirdo.CareerConnect.models.Company;

import java.util.function.BiConsumer;

public enum CompanyField {
    NAME((company, value) -> company.setName((String) value)),
    LOGO((company, value) -> company.setLogo((String) value)),
    WEBSITE((company, value) -> company.setWebsite((String) value)),
    INDUSTRY((company, value) -> company.setIndustry((String) value)),
    DESCRIPTION((company, value) -> company.setDescription((String) value));

    private final BiConsumer<Company, Object> updater;

    CompanyField(BiConsumer<Company, Object> updater) {
        this.updater = updater;
    }

    public void update(Company company, Object value) {
        this.updater.accept(company, value);
    }
}
