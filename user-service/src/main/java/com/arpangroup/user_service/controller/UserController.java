package com.arpangroup.user_service.controller;

import com.arpangroup.user_service.dto.UserTreeNode;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.entity.UserHierarchy;
import com.arpangroup.user_service.mapper.UserMapper;
import com.arpangroup.user_service.repository.UserHierarchyRepository;
import com.arpangroup.user_service.repository.UserRepository;
import com.arpangroup.user_service.service.UserHierarchyService;
import com.arpangroup.user_service.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    private final UserHierarchyService userHierarchyService;
    private final UserMapper mapper;
    private final UserHierarchyRepository userHierarchyRepository;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> users() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserInfo(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<User> updateUserInfo(@PathVariable Long userId, @RequestBody Map<String, Object> fieldsToUpdate) {
        return ResponseEntity.ok(userService.updateUser(userId, fieldsToUpdate));
    }


    // #############################################################################//
    // #############################################################################//


    @GetMapping("/hierarchy")
    public ResponseEntity<List<UserHierarchy>> userHierarch() {
        return ResponseEntity.ok(userHierarchyRepository.findAll());
    }

    @GetMapping("/downline-tree/{userId}")
    public ResponseEntity<UserTreeNode> getDownlineTree(@PathVariable Long userId, @RequestParam(name = "maxLevel", defaultValue = "3") int maxLevel) {
        UserTreeNode tree = userHierarchyService.getDownlineTree(userId, maxLevel);
        return ResponseEntity.ok(tree);
    }
}
