package com.arpangroup.user_service.exception;

import com.arpangroup.user_service.exception.base.UserValidationException;

public class InvalidRequestException extends UserValidationException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
