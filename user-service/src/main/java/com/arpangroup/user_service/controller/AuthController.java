package com.arpangroup.user_service.controller;

import com.arpangroup.user_service.dto.UserCreateRequest;
import com.arpangroup.user_service.dto.UserInfo;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.mapper.UserMapper;
import com.arpangroup.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/register")
    public UserInfo register(@Valid  @RequestBody UserCreateRequest request) {
        User user = userService.registerUser(request);
        return mapper.mapTo(user);
    }
}
