package com.arpangroup.user_service.controller;

import com.arpangroup.user_service.dto.RegistrationRequest;
import com.arpangroup.user_service.dto.UserCreateRequest;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.mapper.UserMapper;
import com.arpangroup.user_service.service.UserService;
import com.arpangroup.user_service.service.registration.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    /*@PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid  @RequestBody UserCreateRequest request) {
        User user = userService.registerUser(request);
        return ResponseEntity.ok(mapper.mapTo(user));
    }*/

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest request) {
        User userResponse = userService.registerUser(mapper.mapTo(request), request.getReferralCode());
        return ResponseEntity.ok(userResponse);
    }
}
