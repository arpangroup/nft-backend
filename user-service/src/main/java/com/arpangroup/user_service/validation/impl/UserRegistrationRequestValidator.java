package com.arpangroup.user_service.validation.impl;

import com.arpangroup.user_service.repository.UserRepository;
import com.arpangroup.user_service.validation.UserValidator;
import exception.DuplicateRecordExceptionUser;
import exception.InvalidRequestExceptionUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRegistrationRequestValidator extends UserValidator {
    private final UserRepository userRepository;

    @Override
    protected void validateUsername(String username) throws InvalidRequestExceptionUser {

    }

    @Override
    protected void validateDuplicateUsername(String username) throws DuplicateRecordExceptionUser {

    }

    @Override
    protected void validatePassword(String password, int minLength, int maxLength, boolean isAlphanumericCheck) throws InvalidRequestExceptionUser {

    }

    @Override
    protected void validateConfirmPassword(String password, String confirmPassword) throws InvalidRequestExceptionUser {

    }

    @Override
    protected void validateDuplicateEmail(String email) throws DuplicateRecordExceptionUser {

    }

    @Override
    protected void validateDuplicateMobile(String mobile) throws DuplicateRecordExceptionUser {

    }

    @Override
    protected void validateReferralId(String referralId) throws DuplicateRecordExceptionUser {

    }
}
