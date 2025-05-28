package com.arpangroup.referral_service.client;

import com.arpangroup.nft_common.dto.UserInfo;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@ConditionalOnProperty(name = "user.provider.type", havingValue = "remote")
@Slf4j
public class RemoteUserClient implements UserClient {
    private final RestClient restClient;

    public RemoteUserClient(@Qualifier("userRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public UserInfo getUserInfo(long userId) {
        log.info("getUserById: {}", userId);
        try {
            return restClient.get()
                    .uri("/users/{id}", userId)
                    .retrieve()
                    .body(UserInfo.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            log.error("Downstream error: status={}, body={}", ex.getStatusCode(), ex.getResponseBodyAsString());
            ex.printStackTrace();
            throw ex; // or handle differently
        }

    }

    @Override
    public List<UserInfo> getUserInfoByIds(@NotNull List<Long> userIds) {
        log.info("getUserByIds: {}", userIds);
        return restClient.post()
                .uri("/users/batch")
                .body(userIds)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    @Override
    public UserInfo getUserByReferralCode(String referralCode) {
        log.info("getUserByReferralCode: {}", referralCode);
        try {
            return restClient.get()
                    .uri("/users/referralCode/{referralCode}", referralCode)
                    .retrieve()
                    .body(UserInfo.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            log.error("Downstream error: status={}, body={}", ex.getStatusCode(), ex.getResponseBodyAsString());
            ex.printStackTrace();
            throw ex; // or handle differently
        }
    }

    @Override
    public Boolean hasDeposit(Long userId) {
        log.info("hasDeposit for userId: {}", userId);
        try {
            return restClient.get()
                    .uri("/users/{userId}/has-deposit", userId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                        throw new IllegalStateException("user-service error");
                    })
                    .body(Boolean.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            log.error("Downstream error: status={}, body={}", ex.getStatusCode(), ex.getResponseBodyAsString());
            ex.printStackTrace();
            throw ex; // or handle differently
        }

    }

    @Override
    public UserInfo deposit(long userId, BigDecimal amount, String remarks) {
        return null;
    }
}
