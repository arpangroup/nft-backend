package com.arpangroup.user_service.mapper;

import com.arpangroup.user_service.dto.RegistrationRequest;
import com.arpangroup.user_service.dto.UserInfo;
import com.arpangroup.user_service.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserInfo mapTo(User user) {
        return UserInfo.builder()
                .username(user.getUsername())
//                .firstname(user.getFirstname())
//                .lastname(user.getLastname())
//                .email(user.getEmail())
//                .mobile(user.getMobile())
//                .referBy(user.getReferBy())
                .build();
    }

    public User mapTo(RegistrationRequest request) {
        return new User(request.getUsername(), request.getReserveBalance());
    }
}
