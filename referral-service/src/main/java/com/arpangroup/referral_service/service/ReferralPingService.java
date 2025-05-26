package com.arpangroup.referral_service.service;

import org.springframework.stereotype.Service;

@Service
public class ReferralPingService {
    public String ping() {
        return "ping from referral-service.....";
    }
}
