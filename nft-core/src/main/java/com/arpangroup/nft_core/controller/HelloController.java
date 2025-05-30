package com.arpangroup.nft_core.controller;

import com.arpangroup.referral_service.ReferralPingService;
import com.arpangroup.user_service.dto.RegistrationRequest;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.service.RegistrationService;
import com.arpangroup.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HelloController {

    @GetMapping
    public String index() {
        return "index";
    }
}
