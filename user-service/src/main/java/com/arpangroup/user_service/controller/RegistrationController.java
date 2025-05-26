package com.arpangroup.user_service.controller;

import com.arpangroup.user_service.dto.RegistrationRequest;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.exception.IdNotFoundException;
import com.arpangroup.user_service.exception.InvalidRequestException;
import com.arpangroup.user_service.mapper.UserMapper;
import com.arpangroup.user_service.repository.UserRepository;
import com.arpangroup.user_service.service.RegistrationService;
import com.arpangroup.user_service.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;
    private final UserServiceImpl userService;
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegistrationRequest request) throws InvalidRequestException {
        User userResponse = registrationService.registerUser(request);
        return ResponseEntity.ok(userResponse);
    }


    @GetMapping("/add-users")
    public ResponseEntity<?> registerUser() {
        addDummyUsers();
        return ResponseEntity.ok(Map.of("result", "success"));
    }

    private void addDummyUsers() {
        User root = userRepository.findById(1L).orElseThrow(()-> new IdNotFoundException("root userId not found"));

        User user1 = userService.registerUser(new User("user1"), root.getReferralCode());
        User user2 = userService.registerUser(new User("user2"), root.getReferralCode());
        User user3 = userService.registerUser(new User("user3"), root.getReferralCode());

        User user1_1 = userService.registerUser(new User("user1_1"), user1.getReferralCode());
        User user1_2 = userService.registerUser(new User("user1_2"), user1.getReferralCode());
        User user2_1 = userService.registerUser(new User("user2_1"), user2.getReferralCode());
        User user2_2 = userService.registerUser(new User("user2_2"), user2.getReferralCode());
        User user3_1 = userService.registerUser(new User("user3_1"), user3.getReferralCode());

        User user1_1_1 = userService.registerUser(new User("user1_1_1"), user1_1.getReferralCode());
        User user1_1_2 = userService.registerUser(new User("user1_1_2"), user1_1.getReferralCode());
        User user2_1_1 = userService.registerUser(new User("user2_1_1"), user2_1.getReferralCode());
        User user2_1_2 = userService.registerUser(new User("user2_1_2"), user2_1.getReferralCode());
        User user3_1_1 = userService.registerUser(new User("user3_1_1"), user3_1.getReferralCode());

        User user1_1_1_1 = userService.registerUser(new User("user1_1_1_1"), user1_1_1.getReferralCode());
        User user1_1_2_1 = userService.registerUser(new User("user1_1_2_1"), user1_1_2.getReferralCode());
        User user1_1_2_2 = userService.registerUser(new User("user1_1_2_2"), user1_1_2.getReferralCode());
        User user3_1_1_1 = userService.registerUser(new User("user3_1_1_1"), user3_1_1.getReferralCode());


        User user1_1_2_2_1 = userService.registerUser(new User("user1_1_2_2_1"), user1_1_2_2.getReferralCode());
        User user1_1_2_2_2 = userService.registerUser(new User("user1_1_2_2_2"), user1_1_2_2.getReferralCode());
        User user3_1_1_1_1 = userService.registerUser(new User("user3_1_1_1_1"), user3_1_1_1.getReferralCode());
        User user3_1_1_1_2 = userService.registerUser(new User("user3_1_1_1_2"), user3_1_1_1.getReferralCode());


        User x = userService.registerUser(new User("x"), user2_1_1.getReferralCode());
        User y = userService.registerUser(new User("Y"), user2_1_1.getReferralCode());

        User x1 = userService.registerUser(new User("x1"), x.getReferralCode());
        User x2 = userService.registerUser(new User("x2"), x.getReferralCode());

        User y1 = userService.registerUser(new User("y1"), y.getReferralCode());
        User y2 = userService.registerUser(new User("y2"), y.getReferralCode());


        User x2_1 = userService.registerUser(new User("x2_1"), x2.getReferralCode());
        User x2_2 = userService.registerUser(new User("x2_2"), x2.getReferralCode());
    }
}
