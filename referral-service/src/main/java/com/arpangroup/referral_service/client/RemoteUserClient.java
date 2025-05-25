package com.arpangroup.referral_service.client;

import com.arpangroup.referral_service.dto.UserInfo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@ConditionalOnProperty(name = "user.provider.type", havingValue = "remote")
@RequiredArgsConstructor
@Slf4j
public class RemoteUserClient implements UserClient {
    private final RestClient restClient;

    @Override
    public UserInfo getUserById(long userId) {
        log.info("getUserById: {}", userId);
        return restClient.get()
                .uri("/users/{id}", userId)
                .retrieve()
                .body(UserInfo.class);
    }

    @Override
    public List<UserInfo> getUserByIds(@NotNull List<Long> userIds) {
        log.info("getUserByIds: {}", userIds);
        return List.of();
    }

    @Override
    public UserInfo updateUser(@NotNull UserInfo userInfo) {
        return restClient.put()
                .uri("/users/{id}", userInfo.getId())
                .body(userInfo) // sets the request body
                .retrieve()
                .body(UserInfo.class);
    }

    @Override
    public UserInfo getUserByReferralCode(String referralCode) {
        log.info("getUserByReferralCode: {}", referralCode);
        return restClient.get()
                .uri("/users/{id}", referralCode)
                .retrieve()
                .body(UserInfo.class);
    }
}
