package com.arpangroup.referral_service.rank.listener;

import com.arpangroup.nft_common.event.FirstDepositEvent;
import com.arpangroup.nft_common.event.UserRegisteredEvent;
import com.arpangroup.referral_service.rank.service.RankEvaluationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


/**
 * [DEPRECATED] This class is now obsolete.
 *
 * Rank update responsibilities are now handled by centralized event dispatchers:
 * - {@link com.arpangroup.referral_service.listener.UserRegisteredEventDispatcher}
 * - {@link com.arpangroup.referral_service.listener.FirstDepositEventDispatcher}
 *
 * This class remains only for reference and backward compatibility (if needed).
 */
@Deprecated
@Component
@ConditionalOnProperty(name = "feature.deprecated-rank-listener.enabled", havingValue = "true", matchIfMissing = false)
@RequiredArgsConstructor
@Order(2)  // lower number means higher priority (executed earlier)
@Slf4j
public class UserRankUpdateListener {
    private final RankEvaluationService rankEvaluationService;

    /**
     * Handles updating the user's rank after a downline user registration event.
     *
     * <p>Note: This event is triggered when a new user registers under a referrer.</p>
     *
     * <b>Pros:</b>
     * <ul>
     *      <li>Immediate recognition of new referrals.</li>
     *      <li>Can motivate referrers early by acknowledging downline growth quickly.</li>
     * </ul>
     *
     * <b>Cons:</b>
     * <ul>
     *      <li>May cause rank inflation due to inactive or fake registrations.</li>
     *      <li>Users who never make deposits or purchases still trigger rank updates,</li>
     *   potentially leading to inaccurate rank assignments.
     * </ul>
     *
     * @param event the UserRegisteredEvent containing the referee's details
     */
    @EventListener
    public void handleUpdateUserRank(UserRegisteredEvent event) {
        //log.info("DISPATCH :: evaluateAndUpdateUserWithReferrerRank for userId: {}", event.getRefereeId());
        //rankEvaluationService.evaluateAndUpdateUserWithReferrerRank(event.getRefereeId(), BigDecimal.ZERO, event.getReferrerId());
    }


    /**
     * Handles updating the user's rank after the downline user's first deposit or purchase.
     *
     * <p>This event ensures that rank updates occur only after the downline user has made a meaningful transaction,
     * which helps maintain system integrity by avoiding premature rank changes.</p>
     *
     * <b>Pros:</b>
     * <ul>
     *      <li>More accurate rank updates based on active user behavior.</li>
     *      <li>Reduces risk of fake or inactive users inflating ranks.</li>
     * </ul>
     *
     * <b>Cons:</b>
     * <ul>
     *      <li>Rank updates are delayed until user makes a deposit or purchase.</li>
     *      <li>Referrers may receive rank updates later than expected.</li>
     * </ul>
     *
     * @param event the FirstDepositEvent containing the user's transaction details
     */
    @EventListener
    public void handleUpdateUserRank(FirstDepositEvent event) {
        //log.info("Listening :: FirstDepositEvent for userId: {}", event.getUserId());
        //rankEvaluationService.evaluateAndUpdateUserWithReferrerRank(event.getUserId(), event.getAmount(), event.getReferrerId());
    }
}
