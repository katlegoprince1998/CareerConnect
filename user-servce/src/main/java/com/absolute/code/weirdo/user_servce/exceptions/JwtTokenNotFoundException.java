package com.absolute.code.weirdo.user_servce.exceptions;

public class JwtTokenNotFoundException extends RuntimeException {
    public JwtTokenNotFoundException(String jwtTokenNotFound) {
    }
}
