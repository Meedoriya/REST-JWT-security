package com.alibi.securityjwt.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AppError extends RuntimeException{
    public AppError(String message) {
        super(message);
    }
}
