package com.arpangroup.nft_common.provider;


import com.arpangroup.nft_common.dto.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserProvider {
    UserInfo getUserInfo(long userId);
    List<UserInfo> getUserInfoByIds(List<Long> userIds);
    UserInfo updateUserInfo(UserInfo userInfo);
    UserInfo updateUserInfo(Long userId, Map<String, Object> fieldsToUpdate);
    UserInfo getUserInfoByReferralCode(String referralCode);
    Boolean hasDeposit(Long userId);
}
