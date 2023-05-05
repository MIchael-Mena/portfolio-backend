package com.portfolioCRUD.portfolio.security.exception;

public class InvalidJwtException extends RuntimeException {
    private final int statusCode;

    public InvalidJwtException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}