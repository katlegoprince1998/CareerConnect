package com.absolute.code.weirdo.company_service.exceptions;

public class FailedToCreateCompanyException extends RuntimeException {
    public FailedToCreateCompanyException(String message) {
        super(message);
    }
}
