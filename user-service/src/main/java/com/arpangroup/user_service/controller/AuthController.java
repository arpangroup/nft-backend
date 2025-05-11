package com.arpangroup.user_service.controller;

import com.arpangroup.user_service.dto.UserCreateRequest;
import com.arpangroup.user_service.dto.UserInfo;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.mapper.UserMapper;
import com.arpangroup.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final UserMapper mapper;

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello";
    }

    @GetMapping("/register")
    public UserInfo register(@Valid @RequestBody UserCreateRequest request) {
        User user = userService.registerUser(request);
        return mapper.mapTo(user);
    }
}
