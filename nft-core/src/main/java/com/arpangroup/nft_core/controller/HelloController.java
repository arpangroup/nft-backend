package com.arpangroup.nft_core.controller;

import com.arpangroup.referral_service.service.ReferralPingService;
import com.arpangroup.user_service.dto.RegistrationRequest;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.service.RegistrationService;
import com.arpangroup.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class HelloController {
    private final UserService userService;
    private final RegistrationService registrationService;
    private final ReferralPingService referralPingService;

//    public HelloController(@Qualifier("userServiceImpl") UserService userService, ) {
//        this.userService = userService;
//    }


    @GetMapping("/hello")
    public List<User> sayHello() {
        return userService.getUsers();
    }

    @GetMapping("/ping")
    public String ping() {
        return referralPingService.ping();
    }

    @GetMapping("/register")
    public User registerDemoUser() {
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("apple");
        request.setReferralCode("REF1");
        return registrationService.registerUser(request);
    }
}
