package com.arpangroup.user_service.exception;

import com.arpangroup.user_service.exception.base.UserValidationException;

public class UserCreateException extends UserValidationException {
    public UserCreateException() {
        super();
    }

    public UserCreateException(String message) {
        super(message);
    }
}
