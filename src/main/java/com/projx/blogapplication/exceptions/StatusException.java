package com.projx.blogapplication.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class StatusException extends RuntimeException{
    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public StatusException(String message) {
        super(message);
    }

    public StatusException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
