package com.arpangroup.user_service.exception;

import com.arpangroup.user_service.exception.base.UserValidationException;

public class UserIdNotFoundException extends UserValidationException {
    public UserIdNotFoundException() {
        super("userId not found");
    }

    public UserIdNotFoundException(String message) {
        super(message);
    }
}
