package com.arpangroup.user_service.service.impl;

import com.arpangroup.user_service.dto.UserCreateRequest;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.service.UserService;
import com.arpangroup.user_service.validation.UserValidator;
import exception.InvalidRequestExceptionUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserValidator validator;

    @Override
    public User registerUser(UserCreateRequest request) throws InvalidRequestExceptionUser {
        validator.validate(request);
        return null;
    }
}
