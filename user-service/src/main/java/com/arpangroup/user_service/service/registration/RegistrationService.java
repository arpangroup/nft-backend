package com.arpangroup.user_service.service.registration;

import com.arpangroup.user_service.dto.UserCreateRequest;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.exception.InvalidRequestExceptionUser;

public interface RegistrationService {
    User registerUser(UserCreateRequest request) throws InvalidRequestExceptionUser;
}
