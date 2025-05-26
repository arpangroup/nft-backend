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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService implements UserService {
    private static final Logger log = LoggerFactory.getLogger(RegistrationService.class);
    private final UserHierarchyService userHierarchyService;
    private final UserValidatorTemplate userValidator;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final ApplicationEventPublisher publisher;

    @Override
    @Transactional
    public User registerUser(@NonNull RegistrationRequest request){
        userValidator.validateRegistrationRequest(request);
        User user = createUser(request);

        publisher.publishEvent(new UserRegisteredEvent(user.getId(), request.getReferralCode(), ReferralBonusTriggerType.FIRST_DEPOSIT));
        return user;
    }

    @Override
    public void handleDeposit(Long userId, double amount) {

    }

    @Override
    public void activateAccount(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(IdNotFoundException::new);


    }

    @Override
    public void checkMinimumRequirements(User user) {

    }

    @Override
    public void approveReferralBonus(Long userId) {

    }

    @Transactional
    private User createUser(RegistrationRequest request) throws UserCreateException {
        log.info("Creating user: {}", request);
        User user = userMapper.mapTo(request);
        user = userRepository.save(user);
        log.info("user created in DB with ID: {}", user.getId());

        // Update the Closure table for user relationship
        User referrer = userRepository.findByReferralCode(request.getReferralCode()).orElse(null);
        if (referrer != null) {
            log.info("Referrer with ID: {} ====> updating the closure table....", referrer.getId());
            userHierarchyService.updateHierarchy(user.getId(), user.getId());
        }
        return user;
    }
}
