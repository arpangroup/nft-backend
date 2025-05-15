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

    @GetMapping("/add-users")
    public ResponseEntity<?> registerUser() {
        RegistrationRequest user2 = new RegistrationRequest("user2", null, 1000);
        RegistrationRequest user3 = new RegistrationRequest("user3", null, 1000);
        RegistrationRequest user4 = new RegistrationRequest("user4", null, 1000);
        RegistrationRequest user5 = new RegistrationRequest("user5", null, 1000);
        RegistrationRequest user6 = new RegistrationRequest("user6", null, 1000);
        RegistrationRequest user7 = new RegistrationRequest("user7", null, 1000);

        userService.registerUser(mapper.mapTo(user2), "ReferBy_user1");
        userService.registerUser(mapper.mapTo(user3), "ReferBy_user1");
        userService.registerUser(mapper.mapTo(user4), "ReferBy_user2");
        userService.registerUser(mapper.mapTo(user5), "ReferBy_user2");
        userService.registerUser(mapper.mapTo(user6), "ReferBy_user3");
        userService.registerUser(mapper.mapTo(user7), "ReferBy_user4");


        return ResponseEntity.ok("successful");
    }
}
