package com.arpangroup.user_service.controller;

import com.arpangroup.user_service.dto.DepositRequest;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.mapper.UserMapper;
import com.arpangroup.user_service.repository.UserRepository;
import com.arpangroup.user_service.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserServiceImpl userService;
    private final UserMapper mapper;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> users() {
        log.info("users......");
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserInfo(@PathVariable Long userId) {
        log.info("getUserInfo for User ID: {}......", userId);
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<User> updateUserInfo(@PathVariable Long userId, @RequestBody Map<String, Object> fieldsToUpdate) {
        log.info("updateUserInfo for User ID: {}, fieldsToUpdate: {}......", userId, fieldsToUpdate);
        return ResponseEntity.ok(userService.updateUser(userId, fieldsToUpdate));
    }

    @PostMapping("/deposit")
    public ResponseEntity<User> deposit(@Valid @RequestBody DepositRequest request) {
        log.info("DepositRequest: {}......", request);
        return ResponseEntity.ok(userService.deposit(request.getUserId(), request.getAmount(), request.getRemarks(), null));
    }
}
