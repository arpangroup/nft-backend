package com.arpangroup.user_service.service;

import com.arpangroup.user_service.dto.RegistrationRequest;
import com.arpangroup.user_service.entity.User;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;

public interface UserService {
    User createUser(User user, String referralCode);
    User updateUser(Long userId, Map<String, Object> fieldsToUpdate);
    User updateUser(User user);

    List<User> getUsers();
    List<User> getUserByIds(List<Long> userIds);
    User getUserById(Long userId);
    User getUserByReferralCode(String referralCode);

//    boolean hasDeposit(Long userId);
//    boolean iasActive(Long userId);
//    void handleDeposit(Long userId, double amount);
//    void activateAccount(Long userId);
//    void checkMinimumRequirements(User user);
//    void approveReferralBonus(Long userId);
}
