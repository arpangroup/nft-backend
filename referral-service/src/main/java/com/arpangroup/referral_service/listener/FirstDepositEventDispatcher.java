package com.arpangroup.referral_service.listener;

import com.arpangroup.nft_common.event.FirstDepositEvent;
import com.arpangroup.referral_service.rank.service.RankEvaluationService;
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
     *
     * @param event the FirstDepositEvent containing the user's transaction details
     */
    @EventListener
    public void handleUpdateUserRank(FirstDepositEvent event) {
        log.info("Listening :: FirstDepositEvent for userId: {}", event.getUserId());
        rankEvaluationService.evaluateAndUpdateUserWithReferrerRank(event.getUserId(), event.getAmount(), event.getReferrerId());
    }
}
