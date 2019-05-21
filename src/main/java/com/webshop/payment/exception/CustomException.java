package com.webshop.payment.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class CustomException extends RuntimeException {

    private HttpStatus httpStatus;
    private List<SubError> subErrors = new ArrayList<>();

    protected CustomException(String errorDescription, HttpStatus httpStatus) {
        super(errorDescription);
        this.httpStatus = httpStatus;
    }

    protected CustomException(String errorDescription, HttpStatus httpStatus, List<SubError> subErrors) {
        super(errorDescription);
        this.httpStatus = httpStatus;
        this.subErrors = subErrors;
    }

}
