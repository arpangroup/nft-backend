package com.arpangroup.referral_service.client;

import com.arpangroup.nft_common.dto.UserInfo;
import com.arpangroup.user_service.mapper.UserMapper;
import com.arpangroup.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@ConditionalOnProperty(name = "user.provider.type", havingValue = "local")
@RequiredArgsConstructor
@Slf4j
public class LocalUserClient implements UserClient {
    private final UserService userService;
    private final UserMapper mapper;

    @Override
    public UserInfo getUserInfo(long userId) {
        log.info("getUserById: {}", userId);
        return mapper.mapTo(userService.getUserById(userId));
    }

    @Override
    public List<UserInfo> getUserInfoByIds(List<Long> userIds) {
        log.info("getUserByIds: {}", userIds);
        return userService.getUsers().stream().map(mapper::mapTo).toList();
    }

    @Override
    public UserInfo getUserByReferralCode(String referralCode) {
        log.info("getUserByReferralCode: {}", referralCode);
        return mapper.mapTo(userService.getUserByReferralCode(referralCode));
    }

    @Override
    public Boolean hasDeposit(Long userId) {
        log.info("hasDeposit for userId: {}", userId);
        return userService.hasDeposit(userId);
    }

    @Override
    public UserInfo deposit(long userId, BigDecimal amount, String remarks,  String metaInfo) {
        log.info("deposit for userId: {}, amount: {}, remarks: {}", userId, amount, remarks);
        return mapper.mapTo(userService.deposit(userId, amount, remarks, metaInfo));
    }

    @Override
    public UserInfo updateUserRank(long userId, int newRank) {
        log.info("updateUserRank for userId: {}, newRank: {}", userId, newRank);
        return mapper.mapTo(userService.updateUserRank(userId, newRank));
    }
}
