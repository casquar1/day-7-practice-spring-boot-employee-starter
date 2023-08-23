package com.thoughtworks.springbootemployee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmployeeInactiveStatusException extends RuntimeException{
    public EmployeeInactiveStatusException() {
        super("Employee is inactive");
    }
}
