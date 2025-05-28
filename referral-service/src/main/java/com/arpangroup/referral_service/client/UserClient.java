package com.arpangroup.referral_service.client;

import com.arpangroup.nft_common.dto.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserClient {
    UserInfo getUserInfo(long userId);
    List<UserInfo> getUserInfoByIds(List<Long> userIds);
    UserInfo getUserByReferralCode(String referralCode);
    Boolean hasDeposit(Long userId);
}
