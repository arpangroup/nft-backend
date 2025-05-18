package com.arpangroup.user_service.controller;

import com.arpangroup.user_service.dto.RegistrationRequest;
import com.arpangroup.user_service.dto.UserTreeNode;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.entity.UserHierarchy;
import com.arpangroup.user_service.exception.InvalidRequestException;
import com.arpangroup.user_service.mapper.UserMapper;
import com.arpangroup.user_service.repository.UserHierarchyRepository;
import com.arpangroup.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper mapper;
    private final UserHierarchyRepository userHierarchyRepository;

    @GetMapping
    public ResponseEntity<List<User>> users() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/hierarchy")
    public ResponseEntity<List<UserHierarchy>> userHierarch() {
        return ResponseEntity.ok(userHierarchyRepository.findAll());
    }


    /*@PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid  @RequestBody UserCreateRequest request) {
        User user = userService.registerUser(request);
        return ResponseEntity.ok(mapper.mapTo(user));
    }*/

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest request) throws InvalidRequestException {
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

        userService.registerUser(mapper.mapTo(user2), "user1");
        userService.registerUser(mapper.mapTo(user3), "user1");
        userService.registerUser(mapper.mapTo(user4), "user2");
        userService.registerUser(mapper.mapTo(user5), "user2");
        userService.registerUser(mapper.mapTo(user6), "user3");
        userService.registerUser(mapper.mapTo(user7), "user4");


        return ResponseEntity.ok("successful");
    }


    @GetMapping("/downline-tree/{userId}")
    public ResponseEntity<UserTreeNode> getDownlineTree(@PathVariable Long userId) {
        UserTreeNode tree = userService.getDownlineTree(userId);
        return ResponseEntity.ok(tree);
    }
}
