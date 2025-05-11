package com.arpangroup.user_service.service;

import com.arpangroup.user_service.dto.UserCreateRequest;
import com.arpangroup.user_service.entity.User;
import exception.InvalidRequestExceptionUser;

public interface UserService {
    User registerUser(UserCreateRequest request) throws InvalidRequestExceptionUser;
}
