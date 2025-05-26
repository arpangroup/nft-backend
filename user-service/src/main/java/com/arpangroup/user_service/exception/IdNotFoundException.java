package com.arpangroup.user_service.exception;

import com.arpangroup.user_service.exception.base.UserValidationException;

public class IdNotFoundException extends UserValidationException {
    public IdNotFoundException() {
        super("userId not found");
    }

    public IdNotFoundException(String message) {
        super(message);
    }
}
