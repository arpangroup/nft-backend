package com.arpangroup.user_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @GetMapping("/auth")
    public String auth() {
        return "authentication";
    }
}
