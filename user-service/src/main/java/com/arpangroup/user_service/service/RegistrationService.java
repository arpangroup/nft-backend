package com.arpangroup.user_service.service;

import com.arpangroup.nft_common.enums.ReferralBonusTriggerType;
import com.arpangroup.nft_common.event.UserRegisteredEvent;
import com.arpangroup.user_service.dto.RegistrationRequest;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.exception.IdNotFoundException;
import com.arpangroup.user_service.exception.InvalidRequestException;
import com.arpangroup.user_service.exception.UserCreateException;
import com.arpangroup.user_service.mapper.UserMapper;
import com.arpangroup.user_service.repository.UserRepository;
import com.arpangroup.user_service.transaction.TransactionRepository;
import com.arpangroup.user_service.validation.UserValidatorTemplate;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {
    private final UserServiceImpl userService;

    private final UserValidatorTemplate userValidator;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public User registerUser(@NonNull RegistrationRequest request){
        log.info("registerUser: {}", request);
        userValidator.validateRegistrationRequest(request);

        // create the new user
        log.info("creating user.......");
        User user = userService.createUser(userMapper.mapTo(request), request.getReferralCode());

        log.info("publishing UserRegisteredEvent.....");
        publisher.publishEvent(new UserRegisteredEvent(user.getId(), request.getReferralCode(), ReferralBonusTriggerType.FIRST_DEPOSIT));
        return user;
    }
}
