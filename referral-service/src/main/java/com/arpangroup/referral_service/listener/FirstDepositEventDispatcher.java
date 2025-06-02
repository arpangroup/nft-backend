package com.arpangroup.referral_service.listener;

import com.arpangroup.nft_common.event.FirstDepositEvent;
import com.arpangroup.referral_service.rank.service.RankEvaluationService;
import com.arpangroup.referral_service.referral.service.ReferralBonusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(2)  // lower number means higher priority (executed earlier)
@Slf4j
public class FirstDepositEventDispatcher {
    private final RankEvaluationService rankEvaluationService;
    private final ReferralBonusService referralBonusService;

    @EventListener
    public void dispatch(FirstDepositEvent event) {
        Long userId = event.getUserId();
        Long referrerId = event.getReferrerId();

        // Step 1: Process the referral bonus for referrerId
        /**
         * Immediately evaluates and applies the referral bonus for the given referee.
         *
         * Unlike {@link #createPendingBonus}, this method does NOT create a pending record.
         * It **directly applies** the referral bonus to the referrer.
         * It assumes that the triggering condition (such as a successful deposit)
         * has already been satisfied and directly applies the bonus to the referrer.
         *
         * Useful when the triggering condition (e.g., successful first deposit)
         * is already fulfilled, and there is no need to wait for the scheduler
         * to evaluate and apply the bonus.
         */
        log.info("FirstDepositEvent: Evaluate Referral Bonus for - Referrer ID: {}, Referee ID: {}", referrerId, userId);
        referralBonusService.evaluateBonus(userId);


        // Step 2: Update the rank of referrerId after the downline user's first deposit or purchase.
        /**
         * Handles updating the user's rank after the downline user's first deposit or purchase.
         *
         * This event ensures that rank updates occur only after the downline user has made a meaningful transaction,
         * which helps maintain system integrity by avoiding premature rank changes.
         *
         * Pros:
         * - More accurate rank updates based on active user behavior.
         * - Reduces risk of fake or inactive users inflating ranks.
         *
         * Cons:
         * - Rank updates are delayed until user makes a deposit or purchase.
         * - Referrers may receive rank updates later than expected.
         */
        log.info("FirstDepositEvent: Evaluate User Rank - User ID: {}", userId);
        rankEvaluationService.evaluateAndUpdateUserRank(userId, event.getAmount());
    }


    @EventListener
    public void handleUpdateUserRank(FirstDepositEvent event) {
    }
}
