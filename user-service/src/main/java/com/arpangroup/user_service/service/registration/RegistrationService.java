package com.arpangroup.user_service.service.registration;

import com.arpangroup.user_service.dto.UserCreateRequest;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.exception.InvalidRequestException;

public interface RegistrationService {
    User registerUser(UserCreateRequest request) throws InvalidRequestException;
}
