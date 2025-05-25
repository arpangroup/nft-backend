package com.arpangroup.referral_service.client;

import com.arpangroup.referral_service.dto.UserInfo;

import java.util.List;

public interface UserClient {
    UserInfo getUserById(long userId);
    List<UserInfo> getUserByIds(List<Long> userIds);
    UserInfo updateUser(UserInfo userInfo);
    UserInfo getUserByReferralCode(String referralCode);
}
