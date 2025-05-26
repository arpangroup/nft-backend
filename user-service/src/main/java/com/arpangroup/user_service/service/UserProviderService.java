package com.arpangroup.user_service.service;

import com.arpangroup.nft_common.dto.UserInfo;
import com.arpangroup.nft_common.provider.UserProvider;
import com.arpangroup.user_service.mapper.UserMapper;
import com.arpangroup.user_service.repository.UserRepository;
import com.arpangroup.user_service.transaction.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserProviderService extends UserServiceImpl implements UserProvider{
    private final UserMapper mapper;
    private final TransactionService transactionService;

    public UserProviderService(UserRepository userRepository, UserHierarchyService userHierarchyService, UserMapper mapper, TransactionService transactionService) {
        super(userRepository, userHierarchyService);
        this.mapper = mapper;
        this.transactionService = transactionService;
    }

    @Override
    public UserInfo getUserInfo(long userId) {
        return mapper.mapTo(super.getUserById(userId));
    }

    @Override
    public List<UserInfo> getUserInfoByIds(List<Long> userIds) {
        return super.getUsers().stream().map(mapper::mapTo).toList();
    }

    @Override
    public UserInfo updateUserInfo(UserInfo userInfo) {
        return null;
    }

    @Override
    public UserInfo updateUserInfo(Long userId, Map<String, Object> fieldsToUpdate) {
        return mapper.mapTo(super.updateUser(userId, fieldsToUpdate));
    }

    @Override
    public UserInfo getUserInfoByReferralCode(String referralCode) {
        return mapper.mapTo(super.getUserByReferralCode(referralCode));
    }

    @Override
    public Boolean hasDeposit(Long userId) {
        return transactionService.hasDepositTransaction(userId);
    }
}
