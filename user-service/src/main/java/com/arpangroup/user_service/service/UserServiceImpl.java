package com.arpangroup.user_service.service;

import com.arpangroup.user_service.dto.UserTreeNode;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.entity.UserHierarchy;
import com.arpangroup.user_service.exception.InvalidRequestException;
import com.arpangroup.user_service.exception.IdNotFoundException;
import com.arpangroup.user_service.mapper.UserMapper;
import com.arpangroup.user_service.processor.LevelService;
import com.arpangroup.user_service.processor.bonus.ReferralBonusProcessor;
import com.arpangroup.user_service.processor.bonus.WelcomeBonusProcessor;
import com.arpangroup.user_service.repository.ReferralRepository;
import com.arpangroup.user_service.repository.UserHierarchyRepository;
import com.arpangroup.user_service.repository.UserRepository;
import com.arpangroup.user_service.transaction.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
When a new user registers, bonuses can be propagated upwards through the referral hierarchy. Here's a simple approach:
    Direct Bonus: The referrer receives a direct bonus when a new user registers using their referral code
    Community Team Rebate: Calculate rebates based on the users level and propagate them upwards.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl {
    private final UserRepository userRepository;
    private final UserHierarchyRepository userHierarchyRepository;
    private final ReferralRepository referralRepository;
    private final TransactionService transactionService;
    private final WelcomeBonusProcessor welcomeBonusProcessor;
    private final ReferralBonusProcessor referralBonusProcessor;
    private final LevelService levelService;
    private final UserMapper userMapper;
    private final ObjectMapper objectMapper;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()-> new IdNotFoundException("userId: " + userId + " not found"));
    }

    public List<User> getUserByIds(List<Long> userIds) {
        return userRepository.findByIdIn(userIds);
    }

    public User getUserByReferralCode(String referralCode) {
        return userRepository.findByReferralCode(referralCode).orElseThrow(() -> new IdNotFoundException("invalid referralCode"));
    }

    public Boolean hasDeposit(Long userId) {
        return transactionService.hasDepositTransaction(userId);
    }

    public User updateUser(Long userId, Map<String, Object> fieldsToUpdate) {
        User user = getUserById(userId);

        fieldsToUpdate.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(User.class, key);
            if (field != null) {
                field.setAccessible(true);
                Object convertedValue = objectMapper.convertValue(value, field.getType());
                ReflectionUtils.setField(field, user, convertedValue );
            } else {
                log.warn("Attempted to patch unknown field: {}", key);
            }
        });
        return userRepository.save(user);
    }

    public User registerUser(User user, final String referralCode) throws InvalidRequestException {
        // validate referralCode
        // Create user
        // Allocate bonus to referrer

        User referrer = userRepository.findByReferralCode(referralCode).orElseThrow(() -> new InvalidRequestException("invalid referralCode"));
        if (referrer != null) {
            user = userRepository.save(user);
            updateHierarchy(referrer.getId(), user.getId());

            // 1. Allocate WelcomeBonus if enabled
            /*welcomeBonusProcessor.calculateIfEnabled(newUser).ifPresent(welcomeBonus -> {
                transactionService.deposit(newUser.getId(), welcomeBonus, TransactionRemarks.WELCOME_BONUS);
            });*/

            // 2. Allocate Referral (Direct) Bonus if enabled
            /*referralBonusProcessor.calculateIfEnabled(newUser).ifPresent(referralAmt -> {
                Referral referral = new Referral(referrer.getId(), newUser.getId(), referralAmt); // directBonus
                referralRepository.save(referral);
                transactionService.deposit(referrer.getId(), referralAmt, TransactionRemarks.REFERRAL_BONUS + " for userId: " + newUser.getId());
            });*/



            //propagateBonus(referrer.getId(), referral.getBonus());

            // Determine the new user's level after registration and hierarchy update
            /*user.setLevel(determineLevel(user.getId(), user.getReserveBalance()));
            userRepository.save(user);*/
            int level = levelService.determineLevel(user);
            user.setLevel(level);
            userRepository.save(user);

            // Recalculate and update the referrer's level
            //referrer.setLevel(determineLevel(referrer.getId(), referrer.getWalletBalance()));
            //userRepository.save(referrer); // Save the updated level for the referrer
        }
        /*else {
            User newUser = userRepository.save(user);
            //UserHierarchy directPath = new UserHierarchy(newUser.getId(), newUser.getId(), 0);
            //userHierarchyRepository.save(directPath);
        }*/
        return user;
    }

    private double calculateDirectBonus(double reserveBalance) {
        // Implement Bonus calculation Logic
        return reserveBalance * 0.05; // Example: 5% Bonus
    }

    private void updateHierarchy(Long referredId, Long newUserId) {
        List<UserHierarchy> pathToSave = new ArrayList<>();

        // Insert direct path
        UserHierarchy directPath = new UserHierarchy(referredId, newUserId, 1);
        pathToSave.add(directPath);

        // Insert paths for all ancestors of the referrer, up to a maximum depth of 3
        List<UserHierarchy> ancestorPaths = userHierarchyRepository.findByDescendant(referredId);
        pathToSave.addAll(
                ancestorPaths.stream()
                        .filter(path -> path.getDepth() < 3) // Restrict to a maximum 3 levels
                        .map(path -> new UserHierarchy(path.getAncestor(), newUserId, path.getDepth() + 1))
                        .toList()
        );

        // Save all paths in a single batch operation
        userHierarchyRepository.saveAll(pathToSave);
    }

    /*private void propagateBonus(Long referredId, double bonusAmount) {
        User referrer = userRepository.findById(referredId).orElse(null);
        if (referrer != null) {
            double rebate = calculateRebate(referrer.getLevel(), "LvA");
            double bonus = bonusAmount * rebate;
            referrer.setWalletBalance(referrer.getWalletBalance() + bonus);
            userRepository.save(referrer);

            // Continue propagating upwards if needed
            propagateBonus(referrer.getId(), bonusAmount);
        }
    }*/

    private double calculateRebate(int level, String communityLevel) {
        switch (level) {
            case 2:
                if (communityLevel.equals("LvA")) return 0.12;
                if (communityLevel.equals("LvB")) return 0.05;
                if (communityLevel.equals("LvC")) return 0.02;
                break;
            case 3:
                if (communityLevel.equals("LvA")) return 0.13;
                if (communityLevel.equals("LvB")) return 0.06;
                if (communityLevel.equals("LvC")) return 0.03;
                break;
            case 4:
                if (communityLevel.equals("LvA")) return 0.15;
                if (communityLevel.equals("LvB")) return 0.07;
                if (communityLevel.equals("LvC")) return 0.05;
                break;
        }
        return 0;
    }

    public int determineLevel(Long userId, double reserveBalance) {
        int levelACount = calculateLevelACount(userId);
        int levelBCount = calculateLevelBCount(userId);
        int levelCCount = calculateLevelCCount(userId);
        int levelBPlusCCount = levelBCount + levelCCount;

        if (reserveBalance >= 50 && reserveBalance <= 100) {
            return 1;
        }
        if (reserveBalance >= 500 && reserveBalance <= 2000 && levelACount >= 4 && levelBPlusCCount >= 5) {
            return 2;
        }
        if (reserveBalance >= 2000 && reserveBalance <= 5000 && levelACount >= 6 && levelBPlusCCount >= 20) {
            return 3;
        }
        if (reserveBalance >= 5000 && reserveBalance <= 10000 && levelACount >= 15 && levelBPlusCCount >= 35) {
            return 4;
        }
        return 0; // Not eligible
    }

    public int calculateLevelACount(Long userId) {
       return userHierarchyRepository.findByAncestor(userId).stream()
               .filter(path -> path.getDepth() == 1)
               .mapToInt(path -> 1)
               .sum();
    }

    public int calculateLevelBCount(Long userId) {
        /*List<Long> levelAIds = referralRepository.findReferredUserIdsByReferrerId(userId);
        int levelBCount = 0;
        for (Long levelAId : levelAIds) {
            levelBCount += referralRepository.countByReferrerId(levelAId);
        }
        return levelBCount;*/

        /*List<Long> levelAIds = userHierarchyRepository.findByAncestor(userId).stream()
                .filter(path -> path.getDepth() == 1)
                .map(UserHierarchy::getDescendant)
                .toList();

        return levelAIds.stream()
                .mapToInt(levelAId -> userHierarchyRepository.findByAncestor(levelAId).stream()
                        .filter(path -> path.getDepth() == 2)
                        .mapToInt(path -> 1)
                        .sum())
                .sum();*/

        List<Long> levelAIds = userHierarchyRepository.findByAncestorAndDepth(userId, 1)
                .stream()
                .map(UserHierarchy::getDescendant)
                .toList();
        return userHierarchyRepository.findByAncestorsAndDepth(levelAIds, 2).size();
    }

    public int calculateLevelCCount(Long userId) {
        /*List<Long> levelAIds = referralRepository.findReferredUserIdsByReferrerId(userId);
        int levelCCount = 0;
        for (Long levelAId : levelAIds) {
            List<Long> levelBIds = referralRepository.findReferredUserIdsByReferrerId(levelAId);
            for (Long levelBId : levelBIds) {
                levelCCount += referralRepository.countByReferrerId(levelBId);
            }
        }
        return levelCCount;*/

        /*List<Long> levelAIds = userHierarchyRepository.findByAncestor(userId).stream()
                .filter(path -> path.getDepth() == 1)
                .map(UserHierarchy::getDescendant)
                .toList();

        return levelAIds.stream()
                .flatMap(levelAId -> userHierarchyRepository.findByAncestor(levelAId).stream()
                        .filter(path -> path.getDepth() == 2)
                        .map(UserHierarchy::getDescendant))
                .mapToInt(levelBId -> userHierarchyRepository.findByAncestor(levelBId).stream()
                        .filter(path -> path.getDepth() == 3)
                        .mapToInt(path -> 1)
                        .sum())
                .sum();*/

        List<Long> levelAIds = userHierarchyRepository.findByAncestorAndDepth(userId, 1)
                .stream()
                .map(UserHierarchy::getDescendant)
                .toList();

        List<Long> levelBIds = userHierarchyRepository.findByAncestorsAndDepth(levelAIds, 2)
                .stream()
                .map(UserHierarchy::getDescendant)
                .toList();
        return userHierarchyRepository.findByAncestorsAndDepth(levelBIds, 3).size();
    }



    // A daily scheduler will update this daily profit
    /*public double calculateDailyProfit(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            switch (user.getLevel()) {
                case 1: return user.getWalletBalance() * 0.018; // 1.80% daily profit
                case 2: return user.getWalletBalance() * 0.021; // 2.10% daily prof
                case 3: return user.getWalletBalance() * 0.026; // 2.60% daily prof
                case 4: return user.getWalletBalance() * 0.031; // 3.10% daily prof
                default: return 0;
            }
        }
        return 0;
    }*/

    public List<UserHierarchy> getDownline(Long userId) {
        return userHierarchyRepository.findByAncestor(userId).stream()
                .filter(path -> path.getDepth() <= 3)
                .collect(Collectors.toList());
    }




    public UserTreeNode getDownlineTree(Long rootUserId, int maxLevel) {
        User root = userRepository.findById(rootUserId).orElse(null);
        if (root == null) return null;

        UserTreeNode rootNode = new UserTreeNode(root.getId(), root.getUsername(), root.getWalletBalance());
        buildTreeRecursively(rootNode, 1, maxLevel); // max level 3
        return rootNode;
    }

    private void buildTreeRecursively(UserTreeNode parentNode, int currentLevel, int maxLevel) {
        if (currentLevel > maxLevel) return;

        List<UserHierarchy> directChildrenPaths = userHierarchyRepository.findByAncestorAndDepth(parentNode.getUserId(), 1);

        for (UserHierarchy path : directChildrenPaths) {
            Long childId = path.getDescendant();
            User childUser = userRepository.findById(childId).orElse(null);
            if (childUser != null) {
                UserTreeNode childNode = new UserTreeNode(childUser.getId(), childUser.getUsername(), childUser.getWalletBalance());
                parentNode.addChild(childNode);
                buildTreeRecursively(childNode, currentLevel + 1, maxLevel);
            }
        }
    }

}
