package com.arpangroup.user_service.validation;

import com.arpangroup.user_service.dto.UserCreateRequest;
import com.arpangroup.user_service.exception.DuplicateRecordExceptionUser;
import com.arpangroup.user_service.exception.InvalidRequestException;
import jakarta.validation.ValidationException;


public abstract class UserValidator {

    public final void validateRegistrationRequest(UserCreateRequest request) throws InvalidRequestException {
        //verifyCaptcha();
        validateDuplicateUsername(request.getUsername());
        if (request.getEmail() != null) {
            validateValidEmailFormat(request.getEmail());
            validateDuplicateEmail(request.getEmail());
        }
        if (request.getMobile() != null) {
            validateMobileFormat(request.getMobile());
            validateDuplicateMobile(request.getMobile());
            isMobileNumberVerified(request.getMobile());
        }
        if (request.getReferralCode() != null){
            validateReferralCode(request.getReferralCode());
        }
    }

    // Abstract methods to be implemented by subclasses
    protected abstract void validateDuplicateUsername(String username) throws DuplicateRecordExceptionUser;
    protected abstract void validateValidEmailFormat(String email) throws DuplicateRecordExceptionUser;
    protected abstract void validateDuplicateEmail(String email) throws DuplicateRecordExceptionUser;
    protected abstract void validateMobileFormat(String mobile) throws DuplicateRecordExceptionUser;
    protected abstract void validateDuplicateMobile(String mobile) throws DuplicateRecordExceptionUser;
    protected abstract boolean isMobileNumberVerified(String mobile) throws ValidationException;
    protected abstract void validateReferralCode(String referralCode) throws DuplicateRecordExceptionUser;
}
