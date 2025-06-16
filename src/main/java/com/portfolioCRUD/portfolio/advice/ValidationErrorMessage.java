package com.portfolioCRUD.portfolio.advice;

import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class ValidationErrorMessage extends ErrorMessage {
    private List<String> errors;

    public ValidationErrorMessage(int statusCode, Date timestamp, String message, String description, List<String> errors) {
        super(statusCode, timestamp, message, description);
        this.errors = errors;
    }

}