package com.arpangroup.referral_service.client;

import com.arpangroup.referral_service.dto.UserInfo;

import java.util.List;

public interface UserClient {
    UserInfo getUserInfo(long userId);
    List<UserInfo> getUserInfoByIds(List<Long> userIds);
    UserInfo updateUserInfo(UserInfo userInfo);
    UserInfo getUserByReferralCode(String referralCode);
    Boolean hasDeposit(Long userId);
}
