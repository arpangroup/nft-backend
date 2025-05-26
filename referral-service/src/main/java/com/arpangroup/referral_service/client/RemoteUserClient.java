package com.arpangroup.referral_service.client;

import com.arpangroup.referral_service.dto.UserInfo;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
//@ConditionalOnProperty(name = "user.provider.type", havingValue = "remote")
@Slf4j
public class RemoteUserClient implements UserClient {
    private final RestClient restClient;

    public RemoteUserClient(@Qualifier("userRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public UserInfo getUserInfo(long userId) {
        log.info("getUserById: {}", userId);
        return restClient.get()
                .uri("/api/v1/provider/users/{id}", userId)
                .retrieve()
                .body(UserInfo.class);
    }

    @Override
    public List<UserInfo> getUserInfoByIds(@NotNull List<Long> userIds) {
        log.info("getUserByIds: {}", userIds);
        return restClient.post()
                .uri("/api/v1/users/batch")
                .body(userIds)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    @Override
    public UserInfo updateUserInfo(@NotNull UserInfo userInfo) {
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

    @Override
    public Boolean hasDeposit(Long userId) {
        return restClient.get()
                .uri("/users/{userId}/has-deposit", userId)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {throw new IllegalStateException("user-service error");})
                .body(Boolean.class);

    }
}
