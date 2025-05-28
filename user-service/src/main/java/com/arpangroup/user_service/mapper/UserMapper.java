package com.arpangroup.user_service.mapper;

import com.arpangroup.nft_common.dto.UserInfo;
import com.arpangroup.user_service.dto.RegistrationRequest;
import com.arpangroup.user_service.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserInfo mapTo(User user) {
        return UserInfo.builder()
                .id(user.getId())
                .referralCode(user.getReferralCode())
                .walletBalance(user.getWalletBalance())
                .username(user.getUsername())
                .level(user.getLevel())
//                .firstname(user.getFirstname())
//                .lastname(user.getLastname())
//                .email(user.getEmail())
//                .mobile(user.getMobile())
//                .referBy(user.getReferBy())
                .build();
    }

    public User mapTo(UserInfo info) {
        return null;
    }

    public User mapTo(RegistrationRequest request) {
        return new User(request.getUsername());
    }
}
