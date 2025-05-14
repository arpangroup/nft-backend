package com.arpangroup.user_service.exception;

import com.arpangroup.user_service.exception.base.UserValidationException;

public class UserNotFoundException extends UserValidationException {
    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
