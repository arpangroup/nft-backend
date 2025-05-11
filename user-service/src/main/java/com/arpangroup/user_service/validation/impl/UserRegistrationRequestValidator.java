package com.arpangroup.user_service.validation.impl;

import com.arpangroup.user_service.repository.UserRepository;
import com.arpangroup.user_service.validation.UserValidator;
import com.arpangroup.user_service.exception.DuplicateRecordExceptionUser;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegistrationRequestValidator extends UserValidator {
    private final UserRepository userRepository;

    @Override
    protected void validateDuplicateUsername(String username) throws DuplicateRecordExceptionUser {

    }

    @Override
    protected void validateValidEmailFormat(String email) throws DuplicateRecordExceptionUser {

    }

    @Override
    protected void validateDuplicateEmail(String email) throws DuplicateRecordExceptionUser {

    }

    @Override
    protected void validateMobileFormat(String mobile) throws DuplicateRecordExceptionUser {

    }

    @Override
    protected void validateDuplicateMobile(String mobile) throws DuplicateRecordExceptionUser {

    }

    @Override
    protected boolean isMobileNumberVerified(String mobile) throws ValidationException {
        return false;
    }

    @Override
    protected void validateReferralId(String referralId) throws DuplicateRecordExceptionUser {

    }
}
