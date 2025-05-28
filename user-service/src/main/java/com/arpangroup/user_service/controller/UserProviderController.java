package com.arpangroup.user_service.controller;

import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/provider/users")
@RequiredArgsConstructor
public class UserProviderController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserInfo(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<User>> getUserInfoByIds(@RequestBody List<Long> userIds) {
        return ResponseEntity.ok(userService.getUserByIds(userIds));
    }

    @GetMapping("/referralCode/{referralCode}")
    public ResponseEntity<User> getUserByReferralCode(@PathVariable String referralCode) {
        return ResponseEntity.ok(userService.getUserByReferralCode(referralCode));
    }

    @GetMapping("/{userId}/has-deposit")
    public ResponseEntity<Boolean> hasDeposit(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.hasDeposit(userId));
    }
}
