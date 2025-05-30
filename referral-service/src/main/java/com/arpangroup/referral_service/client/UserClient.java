package com.arpangroup.referral_service.client;

import com.arpangroup.nft_common.dto.UserInfo;

import java.math.BigDecimal;
import java.util.List;

public interface UserClient {
    UserInfo getUserInfo(long userId);
    List<UserInfo> getUserInfoByIds(List<Long> userIds);
    UserInfo getUserByReferralCode(String referralCode);
    Boolean hasDeposit(Long userId);
    UserInfo deposit(long userId, BigDecimal amount, String remarks);
    UserInfo updateUserRank(long userId, int newRank);
}
