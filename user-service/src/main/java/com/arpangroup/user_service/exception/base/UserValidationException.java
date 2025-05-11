package com.arpangroup.user_service.exception.base;

public class UserValidationException extends RuntimeException{
    public UserValidationException() {
        super();
    }

    public UserValidationException(String message) {
        super(message);
    }
}
