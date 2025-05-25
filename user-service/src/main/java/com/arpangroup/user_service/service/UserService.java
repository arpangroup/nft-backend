package com.arpangroup.user_service.service;

import com.arpangroup.user_service.dto.RegistrationRequest;
import com.arpangroup.user_service.entity.User;
import org.springframework.lang.NonNull;

public interface UserService {
    User registerUser(@NonNull RegistrationRequest registrationRequest);
    void handleDeposit(Long userId, double amount);
    void activateAccount(Long userId);
    void checkMinimumRequirements(User user);
    void approveReferralBonus(Long userId);
}
