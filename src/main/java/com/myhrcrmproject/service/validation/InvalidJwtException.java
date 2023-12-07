package com.myhrcrmproject.service.validation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidJwtException extends RuntimeException {
    public InvalidJwtException(String message) {
        super(message);
    }
}
