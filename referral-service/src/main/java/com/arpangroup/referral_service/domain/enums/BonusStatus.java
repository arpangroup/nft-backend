package com.arpangroup.referral_service.domain.enums;

public enum BonusStatus {
    PENDING,    // Waiting for evaluation
    APPROVED,   // Successfully granted
    REJECTED    // 	Evaluation done, but not qualified
}
