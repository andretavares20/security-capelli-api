package com.andretavares.testesecurity.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ApiErrorMessage {

    private HttpStatus status;
    private List<String> errors;

    public ApiErrorMessage(HttpStatus status, List<String> errors) {
        super();
        this.status = status;
        this.errors = errors;
    }

    public ApiErrorMessage(HttpStatus status, String error) {
        super();
        this.status = status;
        errors = Arrays.asList(error);
    }
    //Getters and Setters
}
