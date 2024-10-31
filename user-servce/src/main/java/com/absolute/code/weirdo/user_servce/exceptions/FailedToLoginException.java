package com.absolute.code.weirdo.user_servce.exceptions;

public class FailedToLoginException extends RuntimeException {
    public FailedToLoginException(String message) {
        super(message);
    }
}
