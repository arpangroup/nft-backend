package com.arpangroup.user_service.validation;

import com.arpangroup.user_service.dto.UserCreateRequest;
import exception.DuplicateRecordExceptionUser;
import exception.InvalidRequestExceptionUser;


public abstract class UserValidator {

    public final void validate(UserCreateRequest request) throws InvalidRequestExceptionUser {
        //verifyCaptcha();
        validateUsername(null);
        validateDuplicateUsername(null);
        validatePassword(null, 6, 15, false);
        validateConfirmPassword(null, null);
        validateDuplicateEmail(null);
        validateDuplicateMobile(null);
        validateReferralId(null);
    }

    // Abstract methods to be implemented by subclasses
    protected abstract void validateUsername(String username) throws InvalidRequestExceptionUser;
    protected abstract void validateDuplicateUsername(String username) throws DuplicateRecordExceptionUser;
    protected abstract void validatePassword(String password, int minLength, int maxLength, boolean isAlphanumericCheck) throws InvalidRequestExceptionUser;
    protected abstract void validateConfirmPassword(String password, String confirmPassword) throws InvalidRequestExceptionUser;
    protected abstract void validateDuplicateEmail(String email) throws DuplicateRecordExceptionUser;
    protected abstract void validateDuplicateMobile(String mobile) throws DuplicateRecordExceptionUser;
    protected abstract void validateReferralId(String referralId) throws DuplicateRecordExceptionUser;
}
